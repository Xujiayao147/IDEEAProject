import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import ArticleList from '@/components/ArticleList'
import PostArticle from '@/components/PostArticle'

Vue.use(Router)

export default new Router({
	routes: [
		{
			path: '/',
			name: 'Login',
			hidden: true,
			component: Login
		}, {
			path: '/home',
			name: '',
			component: Home,
			hidden: true
		}, {
			path: '/home',
			component: Home,
			name: 'Articles',
			iconCls: 'fa fa-file-text-o',
			children: [
				{
					path: '/articleList',
					name: 'List',
					component: ArticleList,
					meta: {
						keepAlive: true
					}
				}, {
					path: '/postArticle',
					name: 'Post',
					component: PostArticle,
					meta: {
						keepAlive: false
					}
				}, {
					path: '/editBlog',
					name: 'Edit',
					component: PostArticle,
					hidden: true,
					meta: {
						keepAlive: false
					}
				}
			]
		}
	]
})
