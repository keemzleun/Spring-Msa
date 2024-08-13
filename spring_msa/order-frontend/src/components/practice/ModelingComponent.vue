<template>
    <!-- HTML 요소의 동적 값 표시 -->
    <!-- html 요소 안에 중괄호 2번을 사용함으로써 script에 정의된 변수값을 화면에 표시 -->
    <h1>hello world {{ language }}</h1>

    <!-- `:value` 문법을 사용하면 단방향 데이터 바인딩(보통 잘 안 쓰는 것 같다) -->
    <input type="text" :value="value1"> 

    <!-- `v-model` 문법을 사용하면 양방향 데이터 바인딩(화면쪽 값도 변경되고, 스크립트쪽 값도 변경됨) -->
    <input type="text" v-model="value2">
    <button @click="showValue">변수변경사항확인</button>

    <h2>{{ count }}</h2>
    <button @click="increament">increament1</button>

    <h1>{{ doubleCount }}</h1>



    <br>
    <input type="number" v-model="count2">
    <button @click="increament2">increament2</button>

</template>
<script>
    export default{
        // data 함수에서는 사용할 변수를 선언함
        data(){
            return{
                language:"python",
                value1: "python",
                value2: "java",
                count: 0,
                count2: 0,
                // isLogin: false
            }
        },
        // computed는 종속된 반응형 데이터(count)가 변경될 때 함수를 다시 실행하여 값을 계산하는 함수
        computed: {
            doubleCount(){
                return this.count * 2;
            }
        },
        created(){
            alert("CREATED 함수 호출")
        },
        methods:{
            showValue(){
                alert(this.value2);
            },
            increament(){
                this.count++;
            },
            increament2(){
                this.count2++
            }
        }
    }
</script>