package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order getOrderById(long id) {
        return this.orderRepository.findById(id).get();
    }

    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return this.orderDetailRepository.findByOrder(order);
    }

    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        Order order = this.orderRepository.findById(id).get();
        if (order != null) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.delete(orderDetail);
            }
        }
        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> optionalOrder = this.orderRepository.findById(order.getId());
        if (optionalOrder != null) {
            Order currentOrder = optionalOrder.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> getOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public Page<Order> findAllOrders(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }
}
