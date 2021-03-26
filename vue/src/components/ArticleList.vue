<template>
	<el-container class="article_list">
		<el-main class="main">
			<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
				<el-tab-pane label="全部文章" name="all">
					<blog_table :activeName="activeName" :showDelete="false" :showEdit="false" :showRestore="false"
					            state="-1"></blog_table>
				</el-tab-pane>
				<el-tab-pane label="已发表" name="post">
					<blog_table :activeName="activeName" :showDelete="true" :showEdit="true" :showRestore="false"
					            state="1"></blog_table>
				</el-tab-pane>
				<el-tab-pane label="草稿箱" name="draft">
					<blog_table :activeName="activeName" :showDelete="true" :showEdit="true" :showRestore="false"
					            state="0"></blog_table>
				</el-tab-pane>
				<el-tab-pane label="回收站" name="dustbin">
					<blog_table :activeName="activeName" :showDelete="true" :showEdit="false" :showRestore="true"
					            state="2"></blog_table>
				</el-tab-pane>
				<el-tab-pane v-if="isAdmin" label="博客管理" name="manage">
					<blog_table :activeName="activeName" :showDelete="true" :showEdit="false" :showRestore="false"
					            state="-2"></blog_table>
				</el-tab-pane>
				<el-tab-pane label="博客配置" name="config">
					<blog_cfg></blog_cfg>
				</el-tab-pane>
			</el-tabs>
		</el-main>
	</el-container>
</template>
<script>
import BlogTable from '@/components/BlogTable'
import BlogCfg from '@/components/BlogCfg'
import {getRequest} from '../utils/api'

export default {
	mounted: function () {
		let _this = this;
		getRequest("/isAdmin").then(resp => {
			if (resp.status === 200) {
				_this.isAdmin = resp.data;
			}
		})
	},
	data() {
		return {
			activeName: 'post',
			isAdmin: false
		};
	},
	methods: {
		handleClick(tab, event) {
//        console.log(tab, event);
		}
	},
	components: {
		'blog_table': BlogTable,
		'blog_cfg': BlogCfg
	}
};
</script>
<style>

.article_list > .main {
	/*justify-content: flex-start;*/
	display: flex;
	flex-direction: column;
	padding-left: 0;
	background-color: #fff;
	padding-top: 0;
	margin-top: 8px;
}
</style>
