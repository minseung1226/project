package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.project.domain.Wishlist;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    @Query("select w from Wishlist w join fetch w.room join fetch w.user where w.user.id=:userId")
    public List<Wishlist> findWishlistsByUserId(Long userId);
}
