<template>
	<el-form v-loading="loading" :rules="rules" class="login-container"
	         label-position="left" label-width="0px">
		<h3 class="login_title">Login</h3>
		<el-form-item prop="account">
			<el-input v-model="loginForm.username" auto-complete="off" placeholder="Username" type="text"></el-input>
		</el-form-item>
		<el-form-item prop="checkPass">
			<el-input v-model="loginForm.password" auto-complete="off" placeholder="Password"
			          type="password"></el-input>
		</el-form-item>
		<el-checkbox v-model="checked" class="login_remember" label-position="left">Remember password</el-checkbox>
		<el-form-item style="width: 100%">
			<el-button style="width: 100%" type="primary" @click.native.prevent="submitClick">Login</el-button>
		</el-form-item>
	</el-form>
</template>
<script>
import {postRequest} from '../utils/api'

export default {
	data() {
		return {
			rules: {
				account: [{required: true, message: 'Enter a username', trigger: 'blur'}],
				checkPass: [{required: true, message: 'Enter a password', trigger: 'blur'}]
			},
			checked: true,
			loginForm: {
				username: 'Xujiayao',
				password: '123'
			},
			loading: false
		}
	},
	methods: {
		submitClick: function () {
			let _this = this;
			this.loading = true;
			postRequest('/login', {
				username: this.loginForm.username,
				password: this.loginForm.password
			}).then(resp => {
				_this.loading = false;
				if (resp.status === 200) {
					//成功
					let json = resp.data;
					if (json.status === 'success') {
						_this.$router.replace({path: '/home'});
					} else {
						_this.$alert('Login failed!', 'Failed!');
					}
				} else {
					//失败
					_this.$alert('Login failed!', 'Failed!');
				}
			}, () => {
				_this.loading = false;
				_this.$alert('Server not found!', 'Failed!');
			});
		}
	}
}
</script>
<style>
.login-container {
	border-radius: 15px;
	background-clip: padding-box;
	margin: 180px auto;
	width: 350px;
	padding: 35px 35px 15px 35px;
	background: #fff;
	border: 1px solid #eaeaea;
	box-shadow: 0 0 25px #cac6c6;
}

.login_title {
	margin: 0 auto 40px auto;
	text-align: center;
	color: #505458;
}

.login_remember {
	margin: 0 0 35px 0;
	text-align: left;
}
</style>
