package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        // item은 JPA에 저장하기 전까지 id가 없다.
        // id가 없다 == 새로 생성한 객체
        if(item.getId() == null){
            em.persist(item);
        } else{
            //이미 DB에 등록된 것을 가져온 것
            em.merge(item); // update와 비슷하다고 생각하면 된다.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
