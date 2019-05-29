import Vue from "vue";
import Router from "vue-router";
import Workspace from "@/views/Workspace";
import Login from "@/views/Login";
import News from "@/views/News";
import Course from "@/views/Course";
import RiskAnalysis from "@/views/RiskAnalysis";
import AnalysisResult from "@/views/AnalysisResult";
import Aboutus from "@/views/Aboutus";
import About from "@/views/About";
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
    },
    {
      path: "/aboutus",
      name: "Aboutus",
      component: Aboutus
    },
    {
      path: "/about",
      name: "About",
      component: About,
      children: [
        {
          path: "project-background",
          name: "ProjectBackground",
          component: () => import("@/views/about/ProjectBackground.vue")
        },
        {
          path: "project-significance",
          name: "ProjectBackground",
          component: () => import("@/views/about/ProjectSignificance.vue")
        },
        {
          path: "software-architecture",
          name: "SoftwareArchitecture",
          component: () => import("@/views/about/SoftwareArchitecture.vue")
        },
        {
          path: "function-introduction",
          name: "FunctionIntroduction",
          component: () => import("@/views/about/FunctionIntroduction.vue")
        },
        {
          path: "contact-us",
          name: "ContactUs",
          component: () => import("@/views/about/ContactUs.vue")
        }
      ]
    },
  ]
});
