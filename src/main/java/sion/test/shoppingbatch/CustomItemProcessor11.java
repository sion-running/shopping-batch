package sion.test.shoppingbatch;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor11 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("CustomItemProcessor11");
        return item;
    }
}
