<template>
<v-container>
    <v-row justify="center">
        <v-col cols="12" md="8">
            <v-card-title class="text-center text-h5">
                주문 목록
            </v-card-title>
            <v-card-text>
                <v-data-table
                    :headers="tableHeaders"
                    :items="orderList"
                    class="elevation-1"
                    show-expand
                >
                <template v-slot:[`item.actions`]="{ item }">
                    <v-btn color="red" v-if="isAdmin && item.orderStatus === 'ORDERED'" @click.stop="cancelOrder(item.id)" size="small">
                        CANCEL
                    </v-btn>
                </template>
                <template v-slot:expanded-row="{ item }">
                    <v-row>
                        <v-col>
                            <v-list-item v-for="orderItem in item.orderDetailDtos" :key="orderItem.id">
                                <v-list-item-content>
                                    <v-list-item-title>
                                        {{ orderItem.productName }} {{ orderItem.count }}개
                                    </v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-col>
                    </v-row>
                </template>
                </v-data-table>
            </v-card-text>
        </v-col>
    </v-row>
</v-container>
    
</template>
<script>
import axios from 'axios';

export default{
    props:['isAdmin'],
    data(){
        return {
            orderList:[],
            tableHeaders:[{title:'ID', key:'id', align:'start'},
                            {title:'회원Email', key:'memberEmail', align:'start'},
                            {title:'주문 상태', key:'orderStatus', align:'start'},
                            {title:'Action', key:'actions', align:'start'},
                        ],

        }
    },
    async created(){
        if(this.isAdmin) {
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/ordering/list`);
            this.orderList = response.data.result;
        }else {
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/ordering/myorders`);
            this.orderList = response.data.result;
         }

    },
    methods:{
        async cancelOrder(id){
            try {
                await axios.patch(`${process.env.VUE_APP_API_BASE_URL}/ordering/${id}/cancel`);
                window.location.reload();
            }catch(e){
                console.log(e);
            } 
        }
    }
}
</script>