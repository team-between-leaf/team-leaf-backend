	create table account_privacy (
        birth_date date,
        is_receive_marketing bit not null,
        is_student bit not null,
        user_detail_id bigint not null auto_increment,
        image varchar(255),
        primary key (user_detail_id)
    );
    
    create table account_detail (
        join_date date,
        last_access date,
        login_fail_count integer not null,
        user_detail_user_detail_id bigint unique,
        user_id bigint not null auto_increment,
        email varchar(255),
        name varchar(255),
        nickname varchar(255),
        password varchar(255),
        phone varchar(255),
        school_address varchar(255),
        shipping_address varchar(255),
        university_name varchar(255),
        work_address varchar(255),
        primary key (user_id),
        foreign key (user_detail_user_detail_id) references account_privacy (user_detail_id)
    );
 
    create table board (
        price integer not null,
        board_id bigint not null auto_increment,
        write_date datetime(6),
        writer_user_id bigint,
        content varchar(255),
        image varchar(255),
        title varchar(255),
        primary key (board_id),
        foreign key (writer_user_id) references account_detail (user_id)
    );
 
    create table category (
        category_id bigint not null auto_increment,
        category varchar(255),
        primary key (category_id)
    );
 
    create table comment (
        comment_id bigint not null auto_increment,
        reply_date datetime(6),
        writer_user_id bigint,
        content varchar(255),
        primary key (comment_id),
        foreign key (writer_user_id) references account_detail (user_id)
    );
    
    create table board_comments (
        board_board_id bigint not null,
        comments_comment_id bigint not null unique,
        foreign key (comments_comment_id) references comment (comment_id),
        foreign key (board_board_id) references board (board_id)  
    );
 
    create table favorite_category (
        favorite_category_id bigint not null auto_increment,
        primary key (favorite_category_id)
    );
 
    create table favorite_category_categories (
        categories_category_id bigint not null unique,
        favorite_category_favorite_category_id bigint not null,
        foreign key (favorite_category_favorite_category_id) references favorite_category (favorite_category_id),
        foreign key (categories_category_id) references category (category_id)
    );
 
    create table favorite_category_users (
        favorite_category_favorite_category_id bigint not null,
        users_user_id bigint not null unique,
        foreign key (favorite_category_favorite_category_id) references favorite_category (favorite_category_id),
        foreign key (users_user_id) references account_detail (user_id)
    );
 
    create table product (
        discount_rate float(53) not null,
        price integer not null,
        product_id bigint not null auto_increment,
        registration_date datetime(6),
        views bigint not null,
        description varchar(255),
        image varchar(255),
        title varchar(255),
        primary key (product_id)
    );
    
    create table review (
        score integer not null,
        review_date datetime(6),
        review_id bigint not null auto_increment,
        writer_user_id bigint unique,
        content varchar(255),
        order_type varchar(255),
        primary key (review_id),
        foreign key (writer_user_id) references account_detail (user_id)
    );
 
    create table product_reviews (
        product_product_id bigint not null,
        reviews_review_id bigint not null unique,
        foreign key (product_product_id) references product (product_id),
        foreign key (reviews_review_id) references review (review_id)
    );
    
    create table cart (
        amount integer not null,
        cart_id bigint not null auto_increment,
        product_product_id bigint,
        user_user_id bigint,
        primary key (cart_id),
        foreign key (product_product_id) references product (product_id),
       foreign key (user_user_id) references account_detail (user_id)
    );
    
    create table order_payment_detail (
        order_detail_id bigint not null auto_increment,
        payment_approval datetime(6),
        payment_request datetime(6),
        card_info varchar(255),
        primary key (order_detail_id)
    );
    
    create table order_detail (
        count integer not null,
        price integer not null,
        order_date datetime(6),
        order_detail_order_detail_id bigint unique,
        order_id bigint not null auto_increment,
        orderer_user_id bigint,
        product_product_id bigint,
        commission varchar(255),
        status varchar(255),
        primary key (order_id),
        foreign key (product_product_id) references product (product_id),
        foreign key (orderer_user_id) references account_detail (user_id),
        foreign key (order_detail_order_detail_id) references order_payment_detail (order_detail_id)
    );
 
    create table search_history (
        search_history_id bigint not null auto_increment,
        search_data varchar(255),
        primary key (search_history_id)
    );
    
    create table account_detail_search_histories (
        account_detail_user_id bigint not null,
        search_histories_search_history_id bigint not null unique,
        foreign key (search_histories_search_history_id) references search_history (search_history_id) ,
        foreign key (account_detail_user_id) references account_detail (user_id)
    );
 
    create table wish (
        amount integer not null,
        product_product_id bigint,
        user_user_id bigint,
        wish_id bigint not null auto_increment,
        primary key (wish_id),
        foreign key (user_user_id) references account_detail (user_id),
        foreign key (product_product_id) references product (product_id)
    );