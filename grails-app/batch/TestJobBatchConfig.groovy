import me.nimavat.Person
import me.nimavat.PersonItemWriter;
import me.nimavat.TestTasklet

import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer


beans {
	
	xmlns batch:"http://www.springframework.org/schema/batch"
	
	batch.job(id:"testJob") {
		batch.step(id:"task-1", next:"step-2") {
			batch.tasklet(ref:"testTasklet")
		}
		batch.step(id:'step-2') {
			batch.tasklet() {
				batch.chunk(reader:'itemReader', writer:'itemWriter', 'commit-interval':"1")
			}
		}
	}
	
	testTasklet(TestTasklet) { bean ->
        bean.autowire = "byName"
    }
	
	itemReader(FlatFileItemReader) { bean ->
		resource = "classpath:test.txt"
		lineMapper = {DefaultLineMapper mapper ->
			lineTokenizer = { DelimitedLineTokenizer tokenizer ->
				names = ['name', 'age', 'location']
			}
			
			fieldSetMapper = {BeanWrapperFieldSetMapper fieldSetMapper ->
				prototypeBeanName = "person"
			}
	
		}
	}
	
	itemWriter(PersonItemWriter)
	
	person(Person) {
		
	}
}