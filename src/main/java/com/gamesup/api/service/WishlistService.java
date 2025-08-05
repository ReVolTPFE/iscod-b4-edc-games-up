package com.gamesup.api.service;

import com.gamesup.api.dto.wishlist.WishlistDto;
import com.gamesup.api.exception.ResourceNotFoundException;
import com.gamesup.api.model.Game;
import com.gamesup.api.model.User;
import com.gamesup.api.model.Wishlist;
import com.gamesup.api.repository.GameRepository;
import com.gamesup.api.repository.UserRepository;
import com.gamesup.api.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishlistRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public List<WishlistDto> getWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId).stream()
                .map(w -> new WishlistDto(w.getId(), userId, w.getGame().getId(), w.getGame().getName()))
                .toList();
    }

    public WishlistDto addToWishlist(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        wishlistRepository.findByUserIdAndGameId(userId, game.getId()).ifPresent(w -> {
            throw new IllegalStateException("Game already in wishlist");
        });

        Wishlist wishlist = new Wishlist();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        wishlist.setUser(user);
        wishlist.setGame(game);

        Wishlist saved = wishlistRepository.save(wishlist);
        return new WishlistDto(saved.getId(), userId, game.getId(), game.getName());
    }

    public void removeFromWishlist(Long userId, Long gameId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found in wishlist"));
        wishlistRepository.delete(wishlist);
    }
}
