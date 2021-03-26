<template>
	<el-row v-loading="loading">
		<el-col :span="24">
			<div style="text-align: left;">
				<el-button icon="el-icon-back" style="padding-bottom: 0;" type="text" @click="goBack">返回</el-button>
			</div>
		</el-col>
		<el-col :span="24">
			<div>
				<div><h3 style="margin-top: 0;margin-bottom: 0">{{ article.title }}</h3></div>
				<div style="width: 100%;margin-top: 5px;display: flex;justify-content: flex-end;align-items: center">
					<div style="display: inline; color: #24292E;margin-left: 50px;margin-right:20px;font-size: 12px;">
						{{ article.nickname }}
					</div>
					<span
						style="color: #24292E;margin-right:20px;font-size: 12px;">浏览 {{
							article.pageView == null ? 0 : article.pageView
						}}</span>
					<span
						style="color: #24292E;margin-right:20px;font-size: 12px;"> {{
							article.editTime | formatDateTime
						}}</span>
					<el-tag v-for="(item,index) in article.tags" :key="index" size="small" style="margin-left: 8px"
					        type="success">{{ item.tagName }}
					</el-tag>
					<span style="margin:0 50px 0 0"></span>
				</div>
			</div>
		</el-col>
		<el-col>
			<div style="text-align: left" v-html="article.htmlContent">
			</div>
		</el-col>
	</el-row>
</template>
<script>
import {getRequest} from '../utils/api'

export default {
	methods: {
		goBack() {
			this.$router.go(-1);
		}
	},
	mounted: function () {
		let aid = this.$route.query.aid;
		this.activeName = this.$route.query.an
		let _this = this;
		this.loading = true;
		getRequest("/article/" + aid).then(resp => {
			if (resp.status === 200) {
				_this.article = resp.data;
			}
			_this.loading = false;
		}, () => {
			_this.loading = false;
			_this.$message({type: 'error', message: '页面加载失败!'});
		});
	},
	data() {
		return {
			article: {},
			loading: false,
			activeName: ''
		}
	}
}
</script>
