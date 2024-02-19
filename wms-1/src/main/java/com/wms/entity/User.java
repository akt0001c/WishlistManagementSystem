package com.wms.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
public class User {

@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name="userId")
 private Integer userId;
 
 @Column(name="email", nullable=false ,unique=true )
 @Email(message="Email should be in valid form like (abc@email.com)")
 @NotEmpty(message="Email cannot be empty or null")
 private String email;
 
 @Column(nullable=false)
 @NotEmpty(message="Name cannot be empty or null")
 private String name;
 
 @Column(nullable=false)
 @NotEmpty(message="Password cannot be empty or null")
 @Pattern(regexp="^[A-Za-z0-9@#]{1,10}$")
 @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
 private String password;
 
 
 
 @Column(nullable=false)
 @NotEmpty(message="Mobile number cannot be empty or null")
 private String mobno;
 
 
 private String location;
 
 @Enumerated(EnumType.STRING)
 @Column(nullable=false ,columnDefinition= "enum('Active','Inactive') default 'Active'" )
 private UserStatus status;
 
 @Column(name="addedAt")
 @Temporal(TemporalType.TIMESTAMP)
 private LocalDateTime addedAt;
 
 @JsonIgnore
 @OneToOne(mappedBy="user",cascade= CascadeType.ALL)
 @JoinColumn(name="userId")
 private Wishlist wishlist;
}
