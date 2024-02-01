package sion.test.shoppingbatch;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor33 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("CustomItemProcessor33");
        return item;
    }
}
