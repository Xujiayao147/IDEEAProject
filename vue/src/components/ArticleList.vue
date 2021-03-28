<template>
	<el-container class="articleList">
		<el-main class="main">
			<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
				<el-tab-pane label="All" name="all">
					<articleTable :activeName="activeName" :showDelete="true" :showEdit="true" :showRestore="false"
					              state="-2"></articleTable>
				</el-tab-pane>
				<el-tab-pane label="Posted" name="post">
					<articleTable :activeName="activeName" :showDelete="true" :showEdit="true" :showRestore="false"
					              state="1"></articleTable>
				</el-tab-pane>
				<el-tab-pane label="Drafts" name="draft">
					<articleTable :activeName="activeName" :showDelete="true" :showEdit="true" :showRestore="false"
					              state="0"></articleTable>
				</el-tab-pane>
				<el-tab-pane label="Recycle Bin" name="dustbin">
					<articleTable :activeName="activeName" :showDelete="true" :showEdit="false" :showRestore="true"
					              state="2"></articleTable>
				</el-tab-pane>
			</el-tabs>
		</el-main>
	</el-container>
</template>
<script>
import {getRequest} from '../utils/api'
import ArticleTable from "./ArticleTable";

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
		'articleTable': ArticleTable
	}
};
</script>
<style>

.articleList > .main {
	/*justify-content: flex-start;*/
	display: flex;
	flex-direction: column;
	padding-left: 0;
	background-color: #fff;
	padding-top: 0;
	margin-top: 8px;
}
</style>
