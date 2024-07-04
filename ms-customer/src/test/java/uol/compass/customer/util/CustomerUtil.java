package uol.compass.customer.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.assertj.core.util.DateUtil;
import uol.compass.customer.constants.Gender;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.model.Customer;

@UtilityClass
public class CustomerUtil {

    @Getter
    private static final Customer customer = new Customer(1L,
            "698.354.265-70",
            "John",
            Gender.MASCULINE,
            "john@gmail.com",
            "https://test.s3.amazonaws.com/profile/test.jpeg",
            DateUtil.parse("1990-01-01"),
            0);

    @Getter
    private static final Customer updatedCustomer = new Customer(1L,
            "698.354.265-70",
            "John",
            Gender.MASCULINE,
            "changed@gmail.com",
            "https://test.s3.amazonaws.com/profile/test.jpeg",
            DateUtil.parse("1990-01-01"),
            0);

    @Getter
    private static final CustomerRequest request = new CustomerRequest(
            customer.getCpf(),
            customer.getName(),
            customer.getGender(),
            customer.getEmail(),
            "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                    "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                    "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                    "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                    "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                    "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                    "hGny82eMnIn1weAAAAAElFTkSuQmCC",
            customer.getBirthDate());

    @Getter
    private static final CustomerRequest updateRequest = new CustomerRequest(
            updatedCustomer.getCpf(),
            updatedCustomer.getName(),
            updatedCustomer.getGender(),
            updatedCustomer.getEmail(),
            request.getPhoto(),
            updatedCustomer.getBirthDate());

}