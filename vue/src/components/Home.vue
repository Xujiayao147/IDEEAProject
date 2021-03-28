<template>
	<el-container class="home_container">
		<el-header>
			<div class="home_title"><i class="fab fa-github"/>
				F3 Online Communication Site
			</div>
			<div class="home_userinfoContainer">
				<el-dropdown @command="handleCommand">
  <span class="el-dropdown-link home_userinfo">
	  <img alt="Avatar" class="avatar" src="static/favicon.jpg">
    {{ currentUserName }}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
  </span>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item command="logout">Logout</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</div>
		</el-header>
		<el-container>
			<el-aside width="200px">
				<el-menu
					class="el-menu-vertical-demo"
					default-active="0" router style="background-color: #ECECEC">
					<template v-for="(item,index) in this.$router.options.routes" v-if="!item.hidden">
						<el-submenu v-if="item.children.length>1" :key="index" :index="index+''">
							<template slot="title">
								<i :class="item.iconCls"></i>
								<span>{{ item.name }}</span>
							</template>
							<el-menu-item v-for="child in item.children" v-if="!child.hidden" :key="child.path"
							              :index="child.path">
								{{ child.name }}
							</el-menu-item>
						</el-submenu>
						<template v-else>
							<el-menu-item :index="item.children[0].path">
								<i :class="item.children[0].iconCls"></i>
								<span slot="title">{{ item.children[0].name }}</span>
							</el-menu-item>
						</template>
					</template>
				</el-menu>
			</el-aside>
			<el-container>
				<el-main>
					<el-breadcrumb separator-class="el-icon-arrow-right">
						<el-breadcrumb-item :to="{ path: '/home' }">Home</el-breadcrumb-item>
						<el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
					</el-breadcrumb>
					<keep-alive>
						<router-view v-if="this.$route.meta.keepAlive"></router-view>
					</keep-alive>
					<router-view v-if="!this.$route.meta.keepAlive"></router-view>
				</el-main>
			</el-container>
		</el-container>
	</el-container>
</template>
<script>
import {getRequest} from '../utils/api'

export default {
	methods: {
		handleCommand(command) {
			let _this = this;
			if (command === 'logout') {
				this.$confirm('Logout?', 'Confirm', {
					confirmButtonText: 'Yes',
					cancelButtonText: 'No',
					type: 'warning'
				}).then(function () {
					getRequest("/logout")
					_this.currentUserName = 'LoggedOut';
					_this.$router.replace({path: '/'});
				}, function () {
					//取消
				})
			}
		}
	},
	mounted: function () {
		let _this = this;
		getRequest("/currentUserName").then(function (msg) {
			_this.currentUserName = msg.data;
		}, function () {
			_this.currentUserName = 'LoggedOut';
		});
	},
	data() {
		return {
			currentUserName: ''
		}
	}
}
</script>
<style>
.home_container {
	height: 100%;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
}

.el-header {
	background-color: #24292E;
	color: #333;
	text-align: center;
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.el-aside {
	background-color: #ECECEC;
}

.el-main {
	background-color: #fff;
	color: #000;
	text-align: center;
}

.home_title {
	color: #fff;
	font-size: 22px;
	display: inline;
}

.avatar {
	border-radius: 50%;
	width: 22px;
	vertical-align: middle;
}

.home_userinfo {
	color: #fff;
	cursor: pointer;
}

.home_userinfoContainer {
	display: inline;
	margin-right: 20px;
}
</style>
