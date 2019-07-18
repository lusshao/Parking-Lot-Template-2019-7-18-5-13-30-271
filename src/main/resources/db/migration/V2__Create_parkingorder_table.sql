create table `parking_order`(
  `id` int primary key auto_increment,
  `car_id` varchar(255) not null,
  `car_in_time` timestamp ,
  `car_out_time` timestamp ,
  `order_status` varchar(255) default 'close',
  `parking_lot_name` varchar
)