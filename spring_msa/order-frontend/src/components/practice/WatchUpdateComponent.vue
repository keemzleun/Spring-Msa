<template>
    <h1>watch 예제</h1>
    <div>count : {{count}}</div>
    <button @click="increamentCount">increamentCount</button>

    <h1>update 예제</h1>
    <div>message: {{ message }}</div>
    <button @click="updateMessage">update message</button>
</template>

<script>
export default{
    data(){
        return {
            count: 0,
            message: "hello java"
        }
    },
    // 특정 변수값의 변경사항을 인지하는 hook 함수
    watch:{
        // 인지하고자 하는 변수값과 일치되는 함수명
        count(){
            alert("count값 변경")
        }
    },
    // dom객체(화면)의 변경사항을 인지하는 hook 함수
    // memo ) watch말고 update만 써도 되는데 원하는 특정 변수의 변경사항만 인지하려면 watch도 함께 쓰는 것이 좋음
    updated(){
        alert("dom 요소 변경")
    },
    methods:{
        increamentCount(){
            this.count++;
        },
        updateMessage(){
            this.message = "hello python"
        }
    }
}

</script>