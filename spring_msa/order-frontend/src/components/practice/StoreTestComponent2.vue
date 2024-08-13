<template>
    <h1>store test component2</h1>
    <p>count: {{getCount}}</p>
    <v-btn @click="incrementCount" color="secondary">increament count</v-btn>

    <input type="text" v-model="message">
    <p>message: {{getMessage}}</p>
    <v-btn @click="updateMessage" color="green">update Message</v-btn>

</template>
<script>
import { mapGetters } from 'vuex';
export default{
    data(){
        return{
            count: 0,
            message: ""
        }
    },
    computed:{  // 참조하고 잇는 변수값이 변경됐을 때 실행되는 함수
        ...mapGetters(['getCount', 'getMessage'])
    },
    methods:{
        incrementCount(){
            // this.count++;
            this.$store.dispatch('incrementCount')
        },
        updateMessage(){
            // this.count++;
            this.$store.dispatch('updateMessage', this.message)
        }
    }
}
</script>