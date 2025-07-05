DROP PROCEDURE IF EXISTS smsLoansDisbursedSummaryReport;
DELIMITER $$

CREATE PROCEDURE smsLoansDisbursedSummaryReport()
    READS SQL DATA
BEGIN
    DECLARE vTotal DECIMAL(22,2);

    -- Drop and create temporary table
    DROP TEMPORARY TABLE IF EXISTS tmp_loans_out;
    CREATE TEMPORARY TABLE tmp_loans_out (
        accountName       VARCHAR(100),
        phone             VARCHAR(50),
        businessName      VARCHAR(100),
        officerName       VARCHAR(100),
        amountDisbursed   DECIMAL(22,2),
        guarantorName     VARCHAR(100),
        guarantorContact  VARCHAR(100),
        clientType        VARCHAR(10)
    );

    -- Populate the temp table with only ONE guarantor per loan (the "first" one)
    INSERT INTO tmp_loans_out (accountName, phone, businessName, officerName,
                               amountDisbursed, guarantorName,
                               guarantorContact, clientType)
    SELECT  
        lps.account_name,
        COALESCE(m.mobile1,'')                                       AS phone,
        COALESCE(bd.businessName,'')                                 AS businessName,
        COALESCE(staffName(nls.gruop_id),'')                         AS officerName,
        CAST(lps.princimpal_amount AS DECIMAL(22,2))                AS amountDisbursed,
        COALESCE(g.gaurantorsName,'')                                AS guarantorName,
        COALESCE(g.gaurantorsContact1,'')                            AS guarantorContact,
        CASE WHEN DATE(acs.creation_date) = CURDATE()
            THEN 'NEW' ELSE 'OLD' END                                AS clientType
    FROM    loanprocessingstore AS lps
    LEFT JOIN new_loan_appstore AS nls
        ON nls.loan_id = CONCAT('newloan', lps.account_number)
    LEFT JOIN businessdetails AS bd
        ON bd.id = nls.OtherGroups2
    -- This subquery ensures only one row per loanTrnId (lowest gaurantorsName, change to MIN(id) if you have PK)
    LEFT JOIN (
        SELECT
            loanTrnId,
            MIN(gaurantorsName) AS gaurantorsName,
            MIN(gaurantorsContact1) AS gaurantorsContact1
        FROM gaurantors
        GROUP BY loanTrnId
    ) AS g ON g.loanTrnId = nls.trn_id
    LEFT JOIN pmms.account_created_store AS acs
        ON acs.account_number = lps.account_number
    LEFT JOIN pmms.`master` AS m
        ON m.account_number  = lps.account_number
    WHERE lps.trn_date = CURDATE()
      AND IFNULL(lps.loan_cycle_status,'') = 'Disbursed';

    -- Grand total
    SELECT IFNULL(SUM(amountDisbursed),0) INTO vTotal FROM tmp_loans_out;
    INSERT INTO tmp_loans_out
        VALUES ('Total', '', '', '', vTotal, '', '', '');

    -- Final output – officer_name last
    SELECT  accountName                       AS account_name,
            phone,
            businessName                      AS business_name,
            FORMAT(amountDisbursed,0)         AS amount_disbursed,
            guarantorName                     AS guarantor_name,
            guarantorContact                  AS guarantor_contact,
            clientType                        AS client_type,
            officerName                       AS officer_name
    FROM    tmp_loans_out;
END $$
DELIMITER ;
