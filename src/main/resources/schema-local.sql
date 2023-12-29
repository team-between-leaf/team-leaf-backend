CREATE TABLE IF NOT EXISTS account_privacy (
                                               birth_date DATE,
                                               is_receive_marketing BIT NOT NULL,
                                               is_student BIT NOT NULL,
                                               user_detail_id BIGINT NOT NULL AUTO_INCREMENT,
                                               image VARCHAR(255),
    PRIMARY KEY (user_detail_id)
    );

CREATE TABLE IF NOT EXISTS account_detail (
                                              join_date DATE,
                                              last_access DATE,
                                              login_fail_count INTEGER NOT NULL,
                                              user_detail_user_detail_id BIGINT UNIQUE,
                                              user_id BIGINT NOT NULL AUTO_INCREMENT,
                                              email VARCHAR(255),
    name VARCHAR(255),
    nickname VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    school_address VARCHAR(255),
    shipping_address VARCHAR(255),
    university_name VARCHAR(255),
    work_address VARCHAR(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_detail_user_detail_id)
    REFERENCES account_privacy (user_detail_id)
    );

CREATE TABLE IF NOT EXISTS board (
                                     price INTEGER NOT NULL,
                                     board_id BIGINT NOT NULL AUTO_INCREMENT,
                                     write_date DATETIME(6),
    writer_user_id BIGINT,
    content VARCHAR(255),
    image VARCHAR(255),
    title VARCHAR(255),
    PRIMARY KEY (board_id),
    FOREIGN KEY (writer_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS category (
                                        category_id BIGINT NOT NULL AUTO_INCREMENT,
                                        category VARCHAR(255),
    PRIMARY KEY (category_id)
    );

CREATE TABLE IF NOT EXISTS comment (
                                       comment_id BIGINT NOT NULL AUTO_INCREMENT,
                                       reply_date DATETIME(6),
    writer_user_id BIGINT,
    content VARCHAR(255),
    PRIMARY KEY (comment_id),
    FOREIGN KEY (writer_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS board_comments (
                                              board_board_id BIGINT NOT NULL,
                                              comments_comment_id BIGINT NOT NULL UNIQUE,
                                              FOREIGN KEY (comments_comment_id)
    REFERENCES comment (comment_id),
    FOREIGN KEY (board_board_id)
    REFERENCES board (board_id)
    );

CREATE TABLE IF NOT EXISTS favorite_category (
                                                 favorite_category_id BIGINT NOT NULL AUTO_INCREMENT,
                                                 PRIMARY KEY (favorite_category_id)
    );

CREATE TABLE IF NOT EXISTS favorite_category_categories (
                                                            categories_category_id BIGINT NOT NULL UNIQUE,
                                                            favorite_category_favorite_category_id BIGINT NOT NULL,
                                                            FOREIGN KEY (favorite_category_favorite_category_id)
    REFERENCES favorite_category (favorite_category_id),
    FOREIGN KEY (categories_category_id)
    REFERENCES category (category_id)
    );

CREATE TABLE IF NOT EXISTS favorite_category_users (
                                                       favorite_category_favorite_category_id BIGINT NOT NULL,
                                                       users_user_id BIGINT NOT NULL UNIQUE,
                                                       FOREIGN KEY (favorite_category_favorite_category_id)
    REFERENCES favorite_category (favorite_category_id),
    FOREIGN KEY (users_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS product (
    discount_rate FLOAT(53) NOT NULL,
    price INTEGER NOT NULL,
    product_id BIGINT NOT NULL AUTO_INCREMENT,
    registration_date DATETIME(6),
    views BIGINT NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255),
    title VARCHAR(255),
    PRIMARY KEY (product_id)
    );

CREATE TABLE IF NOT EXISTS review (
                                      score INTEGER NOT NULL,
                                      review_date DATETIME(6),
    review_id BIGINT NOT NULL AUTO_INCREMENT,
    writer_user_id BIGINT UNIQUE,
    content VARCHAR(255),
    order_type VARCHAR(255),
    PRIMARY KEY (review_id),
    FOREIGN KEY (writer_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS product_reviews (
                                               product_product_id BIGINT NOT NULL,
                                               reviews_review_id BIGINT NOT NULL UNIQUE,
                                               FOREIGN KEY (product_product_id)
    REFERENCES product (product_id),
    FOREIGN KEY (reviews_review_id)
    REFERENCES review (review_id)
    );

CREATE TABLE IF NOT EXISTS cart (
                                    amount INTEGER NOT NULL,
                                    cart_id BIGINT NOT NULL AUTO_INCREMENT,
                                    product_product_id BIGINT,
                                    user_user_id BIGINT,
                                    PRIMARY KEY (cart_id),
    FOREIGN KEY (product_product_id)
    REFERENCES product (product_id),
    FOREIGN KEY (user_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS order_payment_detail (
                                                    order_detail_id BIGINT NOT NULL AUTO_INCREMENT,
                                                    payment_approval DATETIME(6),
    payment_request DATETIME(6),
    card_info VARCHAR(255),
    PRIMARY KEY (order_detail_id)
    );

CREATE TABLE IF NOT EXISTS order_detail (
                                            count INTEGER NOT NULL,
                                            price INTEGER NOT NULL,
                                            order_date DATETIME(6),
    order_detail_order_detail_id BIGINT UNIQUE,
    order_id BIGINT NOT NULL AUTO_INCREMENT,
    orderer_user_id BIGINT,
    product_product_id BIGINT,
    commission VARCHAR(255),
    status VARCHAR(255),
    PRIMARY KEY (order_id),
    FOREIGN KEY (product_product_id)
    REFERENCES product (product_id),
    FOREIGN KEY (orderer_user_id)
    REFERENCES account_detail (user_id),
    FOREIGN KEY (order_detail_order_detail_id)
    REFERENCES order_payment_detail (order_detail_id)
    );

CREATE TABLE IF NOT EXISTS search_history (
                                              search_history_id BIGINT NOT NULL AUTO_INCREMENT,
                                              search_data VARCHAR(255),
    PRIMARY KEY (search_history_id)
    );

CREATE TABLE IF NOT EXISTS account_detail_search_histories (
                                                               account_detail_user_id BIGINT NOT NULL,
                                                               search_histories_search_history_id BIGINT NOT NULL UNIQUE,
                                                               FOREIGN KEY (search_histories_search_history_id)
    REFERENCES search_history (search_history_id),
    FOREIGN KEY (account_detail_user_id)
    REFERENCES account_detail (user_id)
    );

CREATE TABLE IF NOT EXISTS wish (
                                    amount INTEGER NOT NULL,
                                    product_product_id BIGINT,
                                    user_user_id BIGINT,
                                    wish_id BIGINT NOT NULL AUTO_INCREMENT,
                                    PRIMARY KEY (wish_id),
    FOREIGN KEY (user_user_id)
    REFERENCES account_detail (user_id),
    FOREIGN KEY (product_product_id)
    REFERENCES product (product_id)
    );