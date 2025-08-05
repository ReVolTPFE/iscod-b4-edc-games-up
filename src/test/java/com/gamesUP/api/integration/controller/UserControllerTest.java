package com.gamesup.api.integration.controller;

import com.gamesup.api.config.WebMvcTestNoSecurity;
import com.gamesup.api.controller.UserController;
import com.gamesup.api.dto.user.UserPublicDto;
import com.gamesup.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestNoSecurity(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserById_shouldReturnUserWithPurchasedGames() throws Exception {
        UserPublicDto userDto = new UserPublicDto(1L, "User1", List.of("Game1", "Game2"));
        when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User fetched successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.username").value("User1"))
                .andExpect(jsonPath("$.data.purchasedGames[0]").value("Game1"))
                .andExpect(jsonPath("$.data.purchasedGames[1]").value("Game2"));
    }
}
