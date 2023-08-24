package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.project.domain.Wishlist;
import project.project.domain.enum_type.RoomStatus;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    @Query("select w from Wishlist w join fetch w.room join fetch w.user where w.user.id=:userId and w.room.status=:roomStatus")
    public List<Wishlist> findWishlistsByUserId(Long userId, RoomStatus roomStatus);

    @Modifying
    @Transactional
    @Query("delete from Wishlist w where w.id in :wishlistIds")
    public void bulkDeleteByIds(@Param("wishlistIds") List<Long> wishlistIds);
}
