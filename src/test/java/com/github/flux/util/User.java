package com.github.flux.util;

import java.io.Serializable;

public class User implements Serializable{
		private Integer id;
		private String name;
		private Long age;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getAge() {
			return age;
		}
		public void setAge(Long age) {
			this.age = age;
		}
		
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
		}
		
	}