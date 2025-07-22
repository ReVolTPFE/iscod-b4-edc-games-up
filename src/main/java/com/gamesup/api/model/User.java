package com.gamesup.api.model;

import com.gamesup.api.enumeration.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class User {
   
	private int id;
    private String nom;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
