package com.beyond.ordersystem.ordering.Service;

import com.beyond.ordersystem.common.service.StockInventoryService;
import com.beyond.ordersystem.ordering.Controller.SseController;
import com.beyond.ordersystem.ordering.Repository.OrderDetailRepository;
import com.beyond.ordersystem.ordering.Repository.OrderingRepository;
import com.beyond.ordersystem.ordering.domain.OrderDetail;
import com.beyond.ordersystem.ordering.domain.OrderStatus;
import com.beyond.ordersystem.ordering.domain.Ordering;
import com.beyond.ordersystem.ordering.dto.OrderingListResDto;
import com.beyond.ordersystem.ordering.dto.OrderingSaveReqDto;
import com.beyond.ordersystem.ordering.dto.StockDecreaseEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StockInventoryService stockInventoryService;
    private final StockDecreaseEventHandler stockDecreaseEventHandler;
    private final SseController sseController;

    public OrderingService(OrderingRepository orderingRepository,
                           OrderDetailRepository orderDetailRepository,
                           StockInventoryService stockInventoryService,
                           StockDecreaseEventHandler stockDecreaseEventHandler, SseController sseController) {
        this.orderingRepository = orderingRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.stockInventoryService = stockInventoryService;
        this.stockDecreaseEventHandler = stockDecreaseEventHandler;
        this.sseController = sseController;
    }

    // syncronized를 설정한다 하더라도, 재고 감소가 DB에 반영되는 시점은 트랜잭션이 커밋되고 종료되는 시점
    // 방법2. JPA에 최적화된 방식
    public Ordering orderCreate(List<OrderingSaveReqDto> dtos){
        //Authentic 객체안에서 member객체 -> member 이메일 가져옴
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName(); // email 꺼내옴

        // Ordering(주문)객체 만듦 => 아직 save 전이라 id가 null인 것처럼 보여질수있음
        Ordering ordering = Ordering.builder()
                .memberEmail(memberEmail) // 위에서 찾은 member객체
                .orderDetails(new ArrayList<>()) // 아직 아무것도 안들어간 orderDetail 리스트
                .build();

        // 재고감소, 재고 저장에서 동시성이슈 생길 수 있음
//        for (OrderingSaveReqDto dto : dtos){ // OrderingSaveReqDto의 orderDetailDto리스트 요소 하나씩 꺼내옴
//            int quantity = dto.getProductCount();
//            // Product API에 요청을 통해 product객체를 조회해야 함
//
//            // redis를 통한 재고관리 및 재고잔량 확인
//            if (product.getName().contains("sale")) {
//                int newQuantity = (stockInventoryService.decreaseStock(dto.getProductId(), dto.getProductCount())).intValue(); // Long -> int로 형변환
//                if(newQuantity < 0){
//                    throw new IllegalArgumentException("재고부족");
//                }
//                stockDecreaseEventHandler.publish(new StockDecreaseEvent(product.getId(), dto.getProductCount()));
//                // rdb에 재고를 업데이트 -> 이벤트 기반의 아키텍처 구상
//                // rabbitmq를 통해 비동기적으로 이벤트 처리(발생하는 액션기반)
//            }else{
//                if(product.getStockQuantity() < quantity){ // 재고감소 시키는 코드
//                    throw new IllegalArgumentException("재고부족");
//                }
//                // 추가사항이면 save 해줘야하지만 update이므로 save 해줄 필요없음(더티체킹)
//                product.updateStockQuantity(quantity); // 변경감지(dirty checking)로 인해 save 해줄필요 없음
//            }
//
//            OrderDetail orderDetail = OrderDetail.builder() // 주문상세 OrderDetail 객체 조립
//                    .product(product)
//                    .ordering(ordering)
//                    .quantity(quantity)
//                    .build();
//            // OrderDetail Repository를 통해서 저장하는게 아니라 OrderingRepository를 통해서 저장함
//            ordering.getOrderDetails().add(orderDetail); // 여기서 이제 orderDetails 리스트에 orderDetail 하나씩 차례로 add해준다.
//        }

        Ordering savedOrdering = orderingRepository.save(ordering); // 여기서 save해줘도 jpa에 의해서 선후관계 알아서 맞춰서 처리해주기 때문에 코드의 선후관계 안따져도 OK
        // 이제 Ordering 객체를 save 해줬으므로 Ordering 객체의 id값 나올 것 -> 이후 필요한 요소 jpa가 알아서 순서를 처리해준다.
        sseController.publishMessage(savedOrdering.fromEntity(), "admin@test.com");
        return savedOrdering;
    }

    // 주문 목록조회
    public List<OrderingListResDto> orderList(){
        List<Ordering> orderings = orderingRepository.findAll();
        List<OrderingListResDto> orderingListResDtos = new ArrayList<>();

        for(Ordering ordering : orderings){
            orderingListResDtos.add(ordering.fromEntity());
        }
        return orderingListResDtos;
    }

    // 내 주문만 조회
    // 주문 목록조회
    public List<OrderingListResDto> myOrders(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Ordering> orderingList = orderingRepository.findByMemberEmail(email);

        List<OrderingListResDto> orderingListResDtos = new ArrayList<>();

        for(Ordering ordering : orderingList){
            orderingListResDtos.add(ordering.fromEntity());
        }
        return orderingListResDtos;
    }

    public Ordering orderCancel(Long id){
        Ordering ordering = orderingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("주문번호 없음"));
        ordering.updateSatus(OrderStatus.CANCELED); // 더티체킹(Transactional) -> 수정이므로 save 안해줘도 됨.
        return ordering;
    }
}
