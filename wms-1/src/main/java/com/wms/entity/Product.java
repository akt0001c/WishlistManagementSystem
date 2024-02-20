package com.wms.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pid")
	private Integer pid;
	
	@Column(nullable=false)
	@NotEmpty(message="url cannot be empty or null")
	private String pimage;
	
	@Column(nullable=false)
	@NotEmpty(message="product name connot be empty or null")
	private String pname;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	
	
	@Column(name="added_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime addedAt;
	
	@JsonIgnore
	@OneToMany(mappedBy="product" ,cascade=CascadeType.ALL)
	private List<WishlistDetails> wishlistDetails = new ArrayList<>();
	
}
