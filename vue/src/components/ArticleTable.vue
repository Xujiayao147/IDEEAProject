<style type="text/css">
.articleTable_footer {
	display: flex;
	box-sizing: content-box;
	padding-top: 10px;
	padding-bottom: 0;
	margin-bottom: 0;
	justify-content: space-between;
}
</style>
<template>
	<div>
		<div style="display: flex;justify-content: flex-start">
			<el-input
				v-model="keywords"
				placeholder="Search..."
				prefix-icon="el-icon-search" size="mini" style="width: 400px">
			</el-input>
			<el-button icon="el-icon-search" size="mini" style="margin-left: 3px" type="primary" @click="searchClick">
				Search
			</el-button>
		</div>
		<!--<div style="width: 100%;height: 1px;background-color: #24292E;margin-top: 8px;margin-bottom: 0px"></div>-->
		<el-table
			ref="multipleTable"
			v-loading="loading"
			:data="articles"
			max-height="390"
			style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
			tooltip-effect="dark" @selection-change="handleSelectionChange">
			<el-table-column
				v-if="showEdit || showDelete"
				align="left" type="selection" width="35">
			</el-table-column>
			<el-table-column
				align="left"
				label="Title" width="400">
				<template slot-scope="scope"><span style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)">{{
						scope.row.title
					}}</span>
				</template>
			</el-table-column>
			<el-table-column
				align="left" label="Last edited" width="140">
				<template slot-scope="scope">{{ scope.row.editTime | formatDateTime }}</template>
			</el-table-column>
			<el-table-column
				align="left"
				label="Author"
				prop="nickname" width="120">
			</el-table-column>
			<el-table-column v-if="showEdit || showDelete" align="left" label="Action(s)">
				<template slot-scope="scope">
					<el-button
						v-if="showEdit"
						size="mini" @click="handleEdit(scope.$index, scope.row)">Edit
					</el-button>
					<el-button
						v-if="showRestore"
						size="mini" @click="handleRestore(scope.$index, scope.row)">Restore
					</el-button>
					<el-button
						v-if="showDelete"
						size="mini"
						type="danger" @click="handleDelete(scope.$index, scope.row)">Delete
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div class="articleTable_footer">
			<el-button v-show="this.articles.length>0 && showDelete" :disabled="this.selItems.length===0" size="mini"
			           style="margin: 0;"
			           type="danger" @click="deleteSelected">Delete Selected
			</el-button>
			<span></span>
			<el-pagination
				v-show="this.articles.length>0"
				:page-size="pageSize"
				:total="totalCount"
				background layout="prev, pager, next" @current-change="currentChange">
			</el-pagination>
		</div>
	</div>
</template>

<script>
import {getRequest, putRequest} from '../utils/api'
//  import Vue from 'vue'
//  var bus = new Vue()

export default {
	data() {
		return {
			articles: [],
			selItems: [],
			loading: false,
			currentPage: 1,
			totalCount: -1,
			pageSize: 6,
			keywords: '',
			dustbinData: []
		}
	},
	mounted: function () {
		this.loading = true;
		this.loadBlogs(1, this.pageSize);
		let _this = this;
		window.bus.$on('blogTableReload', function () {
			_this.loading = true;
			_this.loadBlogs(_this.currentPage, _this.pageSize);
		})
	},
	methods: {
		searchClick() {
			this.loadBlogs(1, this.pageSize);
		},
		itemClick(row) {
			this.$router.push({path: '/blogDetail', query: {aid: row.id}})
		},
		deleteMany() {
			let selItems = this.selItems;
			for (let i = 0; i < selItems.length; i++) {
				this.dustbinData.push(selItems[i].id)
			}
			this.deleteToDustBin(selItems[0].state)
		},
		//翻页
		currentChange(currentPage) {
			this.currentPage = currentPage;
			this.loading = true;
			this.loadBlogs(currentPage, this.pageSize);
		},
		loadBlogs(page, count) {
			let _this = this;
			let url = '';
			if (this.state === -2) {
				url = "/admin/article/all" + "?page=" + page + "&count=" + count + "&keywords=" + this.keywords;
			} else {
				url = "/article/all?state=" + this.state + "&page=" + page + "&count=" + count + "&keywords=" + this.keywords;
			}
			getRequest(url).then(resp => {
				_this.loading = false;
				if (resp.status === 200) {
					_this.articles = resp.data.articles;
					_this.totalCount = resp.data.totalCount;
				} else {
					_this.$message({type: 'error', message: 'Failed!'});
				}
			}, resp => {
				_this.loading = false;
				if (resp.response.status === 403) {
					_this.$message({type: 'error', message: resp.response.data});
				} else {
					_this.$message({type: 'error', message: 'Failed!'});
				}
			}).catch(() => {
				//压根没见到服务器
				_this.loading = false;
				_this.$message({type: 'error', message: 'Failed!'});
			})
		},
		handleSelectionChange(val) {
			this.selItems = val;
		},
		handleEdit(index, row) {
			this.$router.push({path: '/editBlog', query: {from: this.activeName, id: row.id}});
		},
		handleDelete(index, row) {
			this.dustbinData.push(row.id);
			this.deleteToDustBin(row.state);
		},
		handleRestore(index, row) {
			let _this = this;
			this.$confirm('Restore to original location?', 'Confirm', {
				confirmButtonText: 'Yes',
				cancelButtonText: 'No',
				type: 'warning'
			}).then(() => {
				_this.loading = true;
				putRequest('/article/restore', {articleId: row.id}).then(resp => {
					if (resp.status === 200) {
						let data = resp.data;
						_this.$message({type: data.status, message: data.msg});
						if (data.status === 'success') {
							window.bus.$emit('blogTableReload')//通过选项卡都重新加载数据
						}
					} else {
						_this.$message({type: 'error', message: 'Failed!'});
					}
					_this.loading = false;
				});
			}).catch(() => {
				_this.$message({
					type: 'info',
					message: 'Cancelled'
				});
			});
		},
		deleteToDustBin(state) {
			let _this = this;
			this.$confirm(state !== 2 ? 'Move to recycle bin?' : 'Delete permanently?', 'Confirm', {
				confirmButtonText: 'Yes',
				cancelButtonText: 'No',
				type: 'warning'
			}).then(() => {
				_this.loading = true;
				let url;
				if (_this.state === -2) {
					url = "/admin/article/dustbin";
				} else {
					url = "/article/dustbin";
				}
				putRequest(url, {aids: _this.dustbinData, state: state}).then(resp => {
					if (resp.status === 200) {
						let data = resp.data;
						_this.$message({type: data.status, message: data.msg});
						if (data.status === 'success') {
							window.bus.$emit('blogTableReload')//通过选项卡都重新加载数据
						}
					} else {
						_this.$message({type: 'error', message: 'Failed!'});
					}
					_this.loading = false;
					_this.dustbinData = []
				}, () => {
					_this.loading = false;
					_this.$message({type: 'error', message: 'Failed!'});
					_this.dustbinData = []
				});
			}).catch(() => {
				_this.$message({
					type: 'info',
					message: 'Cancelled'
				});
				_this.dustbinData = []
			});
		}
	},
	props: ['state', 'showEdit', 'showDelete', 'activeName', 'showRestore']
}
</script>
