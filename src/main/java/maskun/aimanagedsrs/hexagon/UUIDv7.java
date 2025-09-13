package maskun.aimanagedsrs.hexagon;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public interface UUIDv7 {
    TimeBasedEpochRandomGenerator generator = Generators.timeBasedEpochRandomGenerator();

    static UUID generate() {
        return generator.generate();
    }
}
