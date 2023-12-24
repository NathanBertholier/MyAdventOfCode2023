package Day5;

import java.util.List;
import java.util.Optional;

public class Mapper {
    public Mapper(List<RangeMapper> rangeMappers) {
        this.rangeMappers = rangeMappers;
    }

    public record RangeMapper(long destination, long source, long range) {
        public boolean inRange(long value) {
            return value >= source && value < source + range;
        }

        public long map(long value) {
            return value - (source - destination);
        }
    }

    private final List<RangeMapper> rangeMappers;

    public long map(long value) {
        Optional<RangeMapper> rangeMapper = rangeMappers.stream().filter(mapper -> mapper.inRange(value)).findFirst();
        return rangeMapper.map(mapper -> mapper.map(value)).orElse(value);
    }
}
