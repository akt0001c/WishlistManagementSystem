package com.wms.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="wishlist_details")
public class WishlistDetails {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="wd_id")
	private Integer wdId;
	
	@Column(nullable=false,columnDefinition="INT default 1")
	private Integer quantity;
	
	@Column(columnDefinition="enum('Added','Purcheshed') default 'Added' ")
	private WishlistProductStatus status;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="product_id")
	private Product product;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="wishlist_id")
	private Wishlist wishlist;
	
	
	@Column(name="added_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime addedAt;
	
	
}
