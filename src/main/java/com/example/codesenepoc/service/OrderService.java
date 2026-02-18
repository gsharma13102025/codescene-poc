package com.example.codesenepoc.service;

import com.example.codesenepoc.model.User;
import com.example.codesenepoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    // Global mutable state - BAD PRACTICE
    private static List<String> globalCache = new ArrayList<>();
    private static Map<Long, Object> tempStorage = new HashMap<>();

    // Extremely long method with multiple responsibilities
    public String processOrder(Long userId, String productCode, int quantity, String shippingAddress,
                               String billingAddress, String paymentMethod, String couponCode,
                               boolean expressShipping, String giftMessage, String specialInstructions) {

        // Deep nesting - CodeScene will flag this
        if (userId != null) {
            if (productCode != null) {
                if (quantity > 0) {
                    if (shippingAddress != null && !shippingAddress.isEmpty()) {
                        if (billingAddress != null && !billingAddress.isEmpty()) {
                            if (paymentMethod != null) {
                                // More nested logic
                                User user = userRepository.findById(userId).orElse(null);
                                if (user != null) {
                                    if (user.getUsername() != null) {
                                        // Duplicated validation logic
                                        if (shippingAddress.length() < 5) {
                                            return "Invalid shipping address";
                                        }
                                        if (billingAddress.length() < 5) {
                                            return "Invalid billing address";
                                        }
                                        if (paymentMethod.equals("CREDIT_CARD")) {
                                            // Credit card processing logic
                                            if (couponCode != null && !couponCode.isEmpty()) {
                                                // Apply coupon
                                                if (couponCode.startsWith("DISCOUNT")) {
                                                    // Calculate discount
                                                    double discount = 0.0;
                                                    if (couponCode.contains("10")) {
                                                        discount = 0.10;
                                                    } else if (couponCode.contains("20")) {
                                                        discount = 0.20;
                                                    } else if (couponCode.contains("30")) {
                                                        discount = 0.30;
                                                    } else if (couponCode.contains("40")) {
                                                        discount = 0.40;
                                                    } else if (couponCode.contains("50")) {
                                                        discount = 0.50;
                                                    }
                                                    // More complex calculations...
                                                }
                                            }

                                            // Shipping calculation with duplication
                                            double shippingCost = 0.0;
                                            if (expressShipping) {
                                                shippingCost = 25.0;
                                                if (quantity > 10) {
                                                    shippingCost = shippingCost + 10.0;
                                                }
                                            } else {
                                                shippingCost = 10.0;
                                                if (quantity > 10) {
                                                    shippingCost = shippingCost + 5.0;
                                                }
                                            }

                                            // Payment processing with magic numbers
                                            if (quantity * 100 > 1000) {
                                                // Large order
                                                return "Order processed with high value";
                                            } else if (quantity * 100 > 500) {
                                                // Medium order
                                                return "Order processed with medium value";
                                            } else {
                                                // Small order
                                                return "Order processed with low value";
                                            }
                                        } else if (paymentMethod.equals("PAYPAL")) {
                                            // PayPal processing - similar complex logic
                                            return "PayPal order processed";
                                        } else if (paymentMethod.equals("BANK_TRANSFER")) {
                                            // Bank transfer processing
                                            return "Bank transfer order processed";
                                        } else {
                                            return "Unknown payment method";
                                        }
                                    } else {
                                        return "User has no username";
                                    }
                                } else {
                                    return "User not found";
                                }
                            } else {
                                return "Payment method required";
                            }
                        } else {
                            return "Billing address required";
                        }
                    } else {
                        return "Shipping address required";
                    }
                } else {
                    return "Quantity must be positive";
                }
            } else {
                return "Product code required";
            }
        } else {
            return "User ID required";
        }
    }

    // Another long method with code duplication
    public double calculatePrice(String productType, int quantity, boolean isMember,
                                 String region, String season) {
        double basePrice = 0.0;

        // Duplicated switch/if logic
        if (productType.equals("ELECTRONICS")) {
            basePrice = 100.0;
            if (isMember) {
                basePrice = basePrice * 0.9; // 10% discount
            }
            if (region.equals("US")) {
                basePrice = basePrice * 1.1; // US tax
            } else if (region.equals("EU")) {
                basePrice = basePrice * 1.2; // EU tax
            } else if (region.equals("ASIA")) {
                basePrice = basePrice * 1.05; // Asia tax
            }
        } else if (productType.equals("CLOTHING")) {
            basePrice = 50.0;
            if (isMember) {
                basePrice = basePrice * 0.85; // 15% discount
            }
            if (region.equals("US")) {
                basePrice = basePrice * 1.1;
            } else if (region.equals("EU")) {
                basePrice = basePrice * 1.2;
            } else if (region.equals("ASIA")) {
                basePrice = basePrice * 1.05;
            }
        } else if (productType.equals("FOOD")) {
            basePrice = 20.0;
            if (isMember) {
                basePrice = basePrice * 0.95; // 5% discount
            }
            if (region.equals("US")) {
                basePrice = basePrice * 1.1;
            } else if (region.equals("EU")) {
                basePrice = basePrice * 1.2;
            } else if (region.equals("ASIA")) {
                basePrice = basePrice * 1.05;
            }
        }

        // More magic numbers
        if (season.equals("SUMMER")) {
            basePrice = basePrice * 0.8;
        } else if (season.equals("WINTER")) {
            basePrice = basePrice * 1.2;
        } else if (season.equals("SPRING")) {
            basePrice = basePrice * 1.0;
        } else if (season.equals("FALL")) {
            basePrice = basePrice * 1.1;
        }

        return basePrice * quantity;
    }

    // Method with too many parameters
    public void updateOrderStatus(Long orderId, String status, String notes, String updatedBy,
                                  String updateReason, String previousStatus, String nextAction,
                                  boolean notifyCustomer, String notificationMethod,
                                  String priority, String category) {
        // Implementation
        globalCache.add("Order " + orderId + " updated by " + updatedBy);
    }
}