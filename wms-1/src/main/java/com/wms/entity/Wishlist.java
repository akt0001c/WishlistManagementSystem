package com.wms.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Wishlist")
public class Wishlist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="wid")
	private Integer wid;
	
	
	@OneToOne( cascade=CascadeType.PERSIST)
	@JoinColumn(name="user_id" ,columnDefinition="INT")
	private User user;
	
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;
	
	
	@OneToMany(mappedBy="wishlist" ,cascade=CascadeType.ALL)
	private List<WishlistDetails> wishlistDetails = new ArrayList<>();
	
	
}
