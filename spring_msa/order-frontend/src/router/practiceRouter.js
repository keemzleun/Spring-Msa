import VuetifyComponent from "@/components/practice/VuetifyComponent.vue";
import ModelingComponent from "@/components/practice/ModelingComponent.vue";
import ConditionalComponent from "@/components/practice/ConditionalComponent.vue";
import HookComponent from "@/components/practice/HookComponent.vue";
import WatchUpdateComponent from "@/components/practice/WatchUpdateComponent.vue";
import AxiosTestComponent from "@/components/practice/AxiosTestComponent.vue";
import RouterTestComponent from "@/components/practice/RouterTestComponent.vue";
import StoreTestComponent from "@/components/practice/StoreTestComponent.vue";
import StoreTestComponent2 from "@/components/practice/StoreTestComponent2.vue";
import StoreTestComponent3 from "@/components/practice/StoreTestComponent3.vue";

export const practiceRouter = [
    {
        path: '/practice/vuetify',
        name: 'VuetifyComponent',
        component: VuetifyComponent
    },
    {
        path: '/practice/modeling',
        name: 'ModelingComponent',
        component: ModelingComponent
    },
    {
        path: '/practice/conditional',
        name: 'ConditionalComponent',
        component: ConditionalComponent
    },
    {
        path: '/practice/hooks',
        name: 'HookComponent',
        component: HookComponent
    },
    {
        path: '/practice/hooks-watch-update',
        name: 'WatchUpdateComponent',
        component: WatchUpdateComponent
    },
    {
        path: '/practice/axiostest',
        name: 'AxiosTestComponent',
        component: AxiosTestComponent
    },
    {
        path: '/practice/routertest',
        name: 'RouterTestComponent',
        component: RouterTestComponent
    },
    {
        path: '/practice/storetest',
        name: 'StoreTestComponent',
        component: StoreTestComponent
    },
    {
        path: '/practice/storetest2',
        name: 'StoreTestComponent2',
        component: StoreTestComponent2
    },
    {
        path: '/practice/storetest3',
        name: 'StoreTestComponent3',
        component: StoreTestComponent3
    },

]