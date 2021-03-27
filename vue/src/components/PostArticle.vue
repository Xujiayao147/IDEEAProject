<template>
	<el-container v-loading="loading" class="post-article">
		<el-header class="header">
			<el-input v-model="article.title" placeholder="请输入标题..." style="width: 400px;margin-left: 10px"></el-input>
			<el-tag
				v-for="tag in article.dynamicTags"
				:key="tag"
				:disable-transitions="false"
				closable
				style="margin-left: 10px" @close="handleClose(tag)">
				{{ tag }}
			</el-tag>
			<el-input
				v-if="tagInputVisible"
				ref="saveTagInput"
				v-model="tagValue"
				class="input-new-tag"
				size="small"
				@blur="handleInputConfirm"
				@keyup.enter.native="handleInputConfirm">
			</el-input>
			<el-button v-else class="button-new-tag" size="small" type="primary" @click="showInput">+Tag</el-button>
		</el-header>
		<el-main class="main">
			<div id="editor">
				<mavon-editor ref=md v-model="article.mdContent" style="height: 100%;width: 100%;"
				              @imgAdd="imgAdd" @imgDel="imgDel"></mavon-editor>
			</div>
			<div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
				<el-button v-if="from!==undefined" @click="cancelEdit">放弃修改</el-button>
				<template v-if="from===undefined || from==='draft'">
					<el-button @click="saveBlog(0)">保存到草稿箱</el-button>
					<el-button type="primary" @click="saveBlog(1)">发表文章</el-button>
				</template>
				<template v-else>
					<el-button type="primary" @click="saveBlog(1)">保存修改</el-button>
				</template>
			</div>
		</el-main>
	</el-container>
</template>
<script>
import {getRequest, postRequest, uploadFileRequest} from '../utils/api'
// Local Registration
import {mavonEditor} from 'mavon-editor'
// 可以通过 mavonEditor.markdownIt 获取解析器markdown-it对象
import 'mavon-editor/dist/css/index.css'
import {isNotNullORBlank} from '../utils/utils'

export default {
	mounted: function () {
		let from = this.$route.query.from;
		this.from = from;
		let _this = this;
		if (from != null && from !== '' && from !== undefined) {
			let id = this.$route.query.id;
			this.id = id;
			this.loading = true;
			getRequest("/article/" + id).then(resp => {
				_this.loading = false;
				if (resp.status === 200) {
					_this.article = resp.data;
					let tags = resp.data.tags;
					_this.article.dynamicTags = []
					for (let i = 0; i < tags.length; i++) {
						_this.article.dynamicTags.push(tags[i].tagName)
					}
				} else {
					_this.$message({type: 'error', message: '页面加载失败!'})
				}
			}, () => {
				_this.loading = false;
				_this.$message({type: 'error', message: '页面加载失败!'})
			})
		}
	},
	components: {
		mavonEditor
	},
	methods: {
		cancelEdit() {
			this.$router.go(-1)
		},
		saveBlog(state) {
			if (!(isNotNullORBlank(this.article.title, this.article.mdContent))) {
				this.$message({type: 'error', message: '数据不能为空!'});
				return;
			}
			let _this = this;
			_this.loading = true;
			postRequest("/article/", {
				id: _this.article.id,
				title: _this.article.title,
				mdContent: _this.article.mdContent,
				htmlContent: _this.$refs.md.d_render,
				cid: _this.article.cid,
				state: state,
				dynamicTags: _this.article.dynamicTags
			}).then(resp => {
				_this.loading = false;
				if (resp.status === 200 && resp.data.status === 'success') {
					_this.article.id = resp.data.msg;
					_this.$message({type: 'success', message: state === 0 ? '保存成功!' : '发布成功!'});
//            if (_this.from != undefined) {
					window.bus.$emit('blogTableReload')
//            }
					if (state === 1) {
						_this.$router.replace({path: '/articleList'});
					}
				}
			}, () => {
				_this.loading = false;
				_this.$message({type: 'error', message: state === 0 ? '保存草稿失败!' : '博客发布失败!'});
			})
		},
		imgAdd(pos, $file) {
			let _this = this;
			// 第一步.将图片上传到服务器.
			let formdata = new FormData();
			formdata.append('image', $file);
			uploadFileRequest("/article/uploadimg", formdata).then(resp => {
				let json = resp.data;
				if (json.status === 'success') {
//            _this.$refs.md.$imgUpdateByUrl(pos, json.msg)
					_this.$refs.md.$imglst2Url([[pos, json.msg]])
				} else {
					_this.$message({type: json.status, message: json.msg});
				}
			});
		},
		imgDel(pos) {

		},
		handleClose(tag) {
			this.article.dynamicTags.splice(this.article.dynamicTags.indexOf(tag), 1);
		},
		showInput() {
			this.tagInputVisible = true;
			this.$nextTick(_ => {
				this.$refs.saveTagInput.$refs.input.focus();
			});
		},
		handleInputConfirm() {
			let tagValue = this.tagValue;
			if (tagValue) {
				this.article.dynamicTags.push(tagValue);
			}
			this.tagInputVisible = false;
			this.tagValue = '';
		}
	},
	data() {
		return {
			tagInputVisible: false,
			tagValue: '',
			loading: false,
			from: '',
			article: {
				id: '-1',
				dynamicTags: [],
				title: '',
				mdContent: '',
				cid: ''
			}
		}
	}
}
</script>
<style>
.post-article > .main > #editor {
	width: 100%;
	height: 450px;
	text-align: left;
}

.post-article > .header {
	background-color: #ececec;
	margin-top: 10px;
	padding-left: 5px;
	display: flex;
	justify-content: flex-start;
}

.post-article > .main {
	/*justify-content: flex-start;*/
	display: flex;
	flex-direction: column;
	padding-left: 5px;
	background-color: #ececec;
	padding-top: 0;
}

.post-article > .header > .button-new-tag {
	margin-left: 10px;
	height: 32px;
	line-height: 30px;
	padding-top: 0;
	padding-bottom: 0;
}

.post-article > .header > .input-new-tag {
	width: 90px;
	margin-left: 10px;
	vertical-align: bottom;
}

.post-article {
}
</style>
