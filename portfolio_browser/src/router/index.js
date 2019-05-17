import Vue from 'vue'
import Router from 'vue-router'
import Workspace from '@/components/Workspace'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/:username/Workspace',
      name: 'Workspace',
      component: Workspace
    }
  ]
})
