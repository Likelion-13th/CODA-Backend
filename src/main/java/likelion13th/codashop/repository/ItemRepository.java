package likelion13th.codashop.repository;

import likelion13th.codashop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
    //아이템 아이디 기반 아이템 찾기
    Optional<Item> findById(Long Item_id);
}
