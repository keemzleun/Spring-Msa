<template>
    <h1>store test component1</h1>
    <StoreTestComponent2/>
    <StoreTestComponent3/>
</template>

<script>
import StoreTestComponent2 from '@/components/practice/StoreTestComponent2.vue';
import StoreTestComponent3 from '@/components/practice/StoreTestComponent3.vue';
export default{
    components:{
        StoreTestComponent2,
        StoreTestComponent3
    }
}
</script>