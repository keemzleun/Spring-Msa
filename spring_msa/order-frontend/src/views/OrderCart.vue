<template>
<v-container>
    <v-row justify="center">
        <v-col class="text-center text-h5">장바구니 목록</v-col>
    </v-row>
    <v-row justify="space-between">
        <v-col cols="auto">
            <v-btn @click="clearCart" color="red">장바구니 비우기</v-btn>
        </v-col>
        <v-col cols="auto">
            <v-btn @click="orderCreate" color="primary">주문하기</v-btn>
        </v-col>
    </v-row>
    <v-row>
        <v-col>
            <v-table>
                <thead>
                    <tr>
                        <th>제품 ID</th>
                        <th>제품명</th>
                        <th>제품 수량</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="product in getCarts" :key="product.id">
                        <td>{{product.id}}</td>
                        <td>{{product.name}}</td>
                        <td>{{product.quantity}}</td>
                    </tr>
                </tbody>
            </v-table>
        </v-col>
    </v-row>
</v-container>
</template>
<script>
import axios from 'axios';
import { mapGetters } from 'vuex';

export default{
    computed:{
        ...mapGetters(['getCarts'])
    },
    methods:{
        clearCart(){
            this.$store.dispatch("clearCart");
        },
        async orderCreate(){
            // key이름이 맞지 않으면 map으로 돌려서 새로운 배열을 만들어 반환
            const orderProducts = this.getCarts.map(p=>{return {productId:p.id, productCount:p.quantity}});
            if (orderProducts.length<1){
                alert("주문 대상 물건이 없습니다");
                return;
            }
            console.log(orderProducts);
            const yesOrNo = confirm(`${orderProducts.length}개의 상품을 주문하시겠습니까?`)
            if(!yesOrNo){
                console.log("주문이 취소되었습니다");
                return;
            }
            try {
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/order-service/ordering/create`, orderProducts);
                alert("주문 완료되었습니다");
                this.clearCart();
            } catch(e){
                console.log(e);
                alert("주문 실패되었습니다.");
            }
        }
    }
}
</script>