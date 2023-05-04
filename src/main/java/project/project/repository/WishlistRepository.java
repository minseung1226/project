package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
}
