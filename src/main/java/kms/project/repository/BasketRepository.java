package kms.project.repository;

import kms.project.aop.Trace;
import kms.project.repository.mapper.BasketMapper;
import kms.project.vo.BasketVO;
import kms.project.vo.BasketViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Repository

@Trace
public class BasketRepository {
    private final BasketMapper basketMapper;

    public BasketRepository(BasketMapper basketMapper) {
        this.basketMapper = basketMapper;
    }

    @Trace
    public BasketVO find_basket(BasketVO basket){
        return basketMapper.find_basket(basket);
    }
    @Trace
    public void update_basket(BasketVO basket){
        basketMapper.update_basket(basket);
    }
    @Trace
    public void insert_basket(BasketVO basket){
        basketMapper.insert_basket(basket);
    }
    @Trace
    public List<BasketViewVO> select_basketView(int user_code){
        return basketMapper.select_basketView(user_code);
    }
    @Trace
    public void delete_basket(String[] basket_code){
        basketMapper.delete_basket(basket_code);
    }
    @Trace
    public void delete_basket(Map<String,Object> map){
        basketMapper.delete_basket2(map);
    }
    @Trace
    public List<BasketViewVO> select_choiceBasket_view(String[] array){
        return basketMapper.select_choiceBasket_view(array);
    }

    @Trace
    public List<BasketViewVO> select_choiceBasket_view(Map<String,Object> map){
        return basketMapper.select_choiceBasket_view2(map);
    }

}
