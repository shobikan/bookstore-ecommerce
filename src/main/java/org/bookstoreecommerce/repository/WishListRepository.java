package org.bookstoreecommerce.repository;

import org.bookstoreecommerce.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    @Query("SELECT w FROM WishList w WHERE w.user.userId = :userId")
    Optional<WishList> findWishListByUserId(Long userId);
}
