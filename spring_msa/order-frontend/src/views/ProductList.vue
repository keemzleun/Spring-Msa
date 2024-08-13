<template>
    <ProductListComponent
    :isAdmin="false"
    pageTitle="상품목록"
/>

</template>
<script>
import ProductListComponent from '@/components/ProductListComponent.vue';
export default{
    components:{
        ProductListComponent
    }
}
</script>