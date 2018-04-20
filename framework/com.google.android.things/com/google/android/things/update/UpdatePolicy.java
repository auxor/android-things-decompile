package com.google.android.things.update;

import com.android.internal.util.Preconditions;
import java.util.concurrent.TimeUnit;

public class UpdatePolicy {
    final long applyDeadline;
    final int policy;

    public static class Builder {
        private Long mApplyDeadline;
        private Integer mPolicy;

        public Builder setPolicy(int policy) {
            switch (policy) {
                case 1:
                case 2:
                case 3:
                    this.mPolicy = Integer.valueOf(policy);
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid update policy value");
            }
        }

        public Builder setApplyDeadline(long deadline, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            if (deadline <= 0) {
                throw new IllegalArgumentException("deadline must be greater than 0");
            }
            this.mApplyDeadline = Long.valueOf(unit.toMillis(deadline));
            return this;
        }

        public UpdatePolicy build() {
            if (this.mPolicy == null) {
                throw new IllegalArgumentException("The update policy must be specified");
            } else if (this.mApplyDeadline != null) {
                return new UpdatePolicy(this.mPolicy.intValue(), this.mApplyDeadline.longValue());
            } else {
                throw new IllegalArgumentException("The update apply deadline must be specified");
            }
        }
    }

    UpdatePolicy(int policy, long applyDeadline) {
        this.policy = policy;
        this.applyDeadline = applyDeadline;
    }
}
