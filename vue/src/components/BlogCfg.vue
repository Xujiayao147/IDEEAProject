<template>
	<el-card v-loading="loading" style="width: 500px">
		<div>
			<div style="text-align: left">
				<el-form ref="emailValidateForm" :model="emailValidateForm" label-position="top"
				         style="color:#24292E;font-size: 14px;">
					<el-form-item
						:rules="[{type: 'email', message: '邮箱格式不对哦!'}]"
						label="开启博客评论通知"
						prop="email">
						<el-input v-model.email="emailValidateForm.email" auto-complete="off" placeholder="请输入邮箱地址..."
						          size="mini"
						          style="width: 300px" type="email"></el-input>
						<el-button size="mini" type="primary" @click="submitForm('emailValidateForm')">确定</el-button>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</el-card>
</template>
<script>
import {getRequest, putRequest} from '../utils/api'

export default {
	data() {
		return {
			emailValidateForm: {
				email: ''
			},
			loading: false
		}
	},
	mounted: function () {
		let _this = this;
		getRequest("/currentUserEmail").then(resp => {
			if (resp.status === 200) {
				_this.emailValidateForm.email = resp.data;
			}
		});
	},
	methods: {
		submitForm(formName) {
			let _this = this;
			this.$refs[formName].validate((valid) => {
				if (valid) {
					_this.loading = true;
					putRequest("/updateUserEmail", {email: _this.emailValidateForm.email}).then(resp => {
						_this.loading = false;
						if (resp.status === 200) {
							_this.$message({type: resp.data.status, message: resp.data.msg});
						} else {
							_this.$message({type: 'error', message: '开启失败!'});
						}
					});
				} else {
					_this.$message({type: 'error', message: '邮箱格式不对哦!'})
					return false;
				}
			});
		}
	}
}
</script>
