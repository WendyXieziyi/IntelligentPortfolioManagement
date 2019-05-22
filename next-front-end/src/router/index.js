import Vue from "vue";
import Router from "vue-router";
import Workspace from "@/views/Workspace";
import Login from "@/views/Login";
import News from "@/views/News";
import Course from "@/views/Course";
import RiskAnalysis from "@/views/RiskAnalysis";
import AnalysisResult from "@/views/AnalysisResult";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "News",
      component: News
    },
    {
      path: "/course",
      name: "Course",
      component: Course,
      children: [
        {
          path: "",
          name: "EntryManual",
          component: () => import("@/views/course/EntryManual.vue")
        },
        {
          path: "risk-education",
          name: "RiskEducation",
          component: () => import("@/views/course/RiskEducation.vue")
        },
        {
          path: "securities-topic",
          name: "SecuritiesTopic",
          component: () => import("@/views/course/SecuritiesTopic.vue")
        },
        {
          path: "stock-option",
          name: "StockOption",
          component: () => import("@/views/course/StockOption.vue")
        },
        {
          path: "virtual-currency",
          name: "VirtualCurrency",
          component: () => import("@/views/course/VirtualCurrency.vue")
        }
      ]
    },
    {
      path: "/risk",
      name: "RiskAnalysis",
      component: RiskAnalysis
    },
    {
      path: "/risk/:point",
      name: "AnalysisResult",
      component: AnalysisResult
    },
    {
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path: "/workspace/:username",
      name: "Workspace",
      component: Workspace
    }
  ]
});
