package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private Collection<OrderItem> orderItem;

//    public Collection<OrderItem> getOrderItem() {
//        return orderItem;
//    }
//
//    public void setOrderItem(Collection<OrderItem> orderItem) {
//        this.orderItem = orderItem;
//    }

    // 비즈니스 로직

    /** stock 증가 +*/
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int resultStock = this.stockQuantity - quantity;
        if(resultStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resultStock;
    }
}
