CREATE TABLE products (
    "id" SERIAL PRIMARY KEY NOT NULL,
    "name" VARCHAR(100) NOT NULL,
    "description" VARCHAR(1000),
    "cost" DECIMAL(16,2),
    "amount" INTEGER NOT NULL DEFAULT 0,
    "type" VARCHAR(30)
);

CREATE TABLE orders(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "client_id" INT NOT NULL,
    "status" VARCHAR(45) NOT NULL,
    "total_cost" NUMERIC(20,2)
);

CREATE TABLE orders_products(
    "order_id" INT NOT NULL,
    "product_id" INT NOT NULL,
    FOREIGN KEY ("order_id") REFERENCES Orders ("id") ON DELETE CASCADE,
    FOREIGN KEY ("product_id") REFERENCES Products ("id") ON DELETE SET NULL,
    PRIMARY KEY ("order_id", "product_id")
);

CREATE TABLE images(
    "id" SERIAL PRIMARY KEY NOT NULL,
    "product_id" INT REFERENCES Products ("id") ON DELETE CASCADE,
    "name" VARCHAR(100) NOT NULL,
    "original_name" VARCHAR(100),
    "content_type" VARCHAR(100) NOT NULL,
    "is_preview" BOOLEAN DEFAULT false,
    "size" INT NOT NULL DEFAULT 0,
    "bytes" BYTEA NOT NULL
);