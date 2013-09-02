package me.nimavat

import java.util.List;

import org.springframework.batch.item.ItemWriter


class PersonItemWriter implements ItemWriter<Person> {

	@Override
   public void write(List<? extends Person> persons) throws Exception {
		persons.each { p ->
			println "person: $p.name:$p.age:$p.location"
		}
   }
}
