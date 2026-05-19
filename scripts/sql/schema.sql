-- 1. Table Parts
CREATE TABLE parts (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    part_number NVARCHAR(20) NOT NULL UNIQUE, -- Format: PART-XXXX-XXX
    name NVARCHAR(100) NOT NULL,
    stock_qty INT NOT NULL DEFAULT 0 CHECK (stock_qty >= 0),
    min_stock INT NOT NULL DEFAULT 0 CHECK (min_stock >= 0),
    created_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    updated_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET()
);

-- 2. Table Loan Orders
CREATE TABLE loan_orders (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    borrower_name NVARCHAR(100) NOT NULL,
    loan_date DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    status NVARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, APPROVED, REJECTED, RETURNED
    created_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET()
);

-- 3. Table Loan Items
CREATE TABLE loan_items (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    loan_id UNIQUEIDENTIFIER NOT NULL,
    part_id UNIQUEIDENTIFIER NOT NULL,
    qty INT NOT NULL CHECK (qty > 0),
    CONSTRAINT FK_LoanItems_Orders FOREIGN KEY (loan_id) REFERENCES loan_orders(id) ON DELETE CASCADE,
    CONSTRAINT FK_LoanItems_Parts FOREIGN KEY (part_id) REFERENCES parts(id)
);

-- 4. Stock Alerts
CREATE TABLE stock_alerts (
    id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    part_id UNIQUEIDENTIFIER NOT NULL,
    current_stock INT NOT NULL,
    threshold INT NOT NULL,
    status NVARCHAR(20) DEFAULT 'NEW', -- NEW, PROCESSED, IGNORED
    triggered_at DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),
    CONSTRAINT FK_StockAlerts_Parts FOREIGN KEY (part_id) REFERENCES parts(id)
);

-- 5. Spring Modulith Event Publication Table
CREATE TABLE event_publication (
    id UNIQUEIDENTIFIER NOT NULL,
    completion_attempts INT,
    completion_date DATETIME2,
    event_type VARCHAR(512) NOT NULL,
    last_resubmission_date DATETIME2,
    listener_id VARCHAR(512) NOT NULL,
    publication_date DATETIME2 NOT NULL,
    serialized_event VARCHAR(MAX) NOT NULL,
    status VARCHAR(255),
    PRIMARY KEY (id)
);