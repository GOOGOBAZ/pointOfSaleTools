var express = require('express');
var router = express.Router();
const dbCon=require("../service/dbLoanServices");
const auth=require("../../auth/service/otherAuthServices");
const createError=require("http-errors");
var sharedDb = require('../../../shared/sharedServices/sharedDbService');



 router.get('/getTxnDetails', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.getTxnDetails());
}catch(error){
    console.table(error);
    next(error);
}
});



 router.post('/postTxnCustomer', auth.verifyAccessToken, async function(req, res,next) {

     var posted;
    try{
             if(!await dbCon.testWtherTxnExist(req.body)){

    posted=await dbCon.postTheTxnCustomer(req.body);



     if(posted){
           
              
              
              res.status(200).json(posted);
              
              if(await sharedDb.checkSMSBalance()){
                console.log(posted);
                // await  sms.sendSMS(sms.smsTheDetails(posted.userPhone1,"Dear "+posted.userName+", Thunderbolt has succussfully registerd your details but pending Approval."+" Please contact your immediate supervisor for Approval."))

                switch(posted.txnDetailsId){
                    case 2000:
                        break;
                        case 2001:
                        break;
                           case 2002:
                        break;
                           case 2003:
                        break;
                           case 2004:
                        break;
                           case 2005:
                        break;
                           case 2006:
                        break;
                           case 2007:
                        break;
                           case 2008:
                        break;
                         case 2009:
                        break;

                }

                
//     "": 2000,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "BODABODALOAN",
//     "txnDetailsTypeName": "LOANDISBURSEMENT"
//   },
//   {
//     "txnDetailsId": 2001,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "BODABODALOAN",
//     "txnDetailsTypeName": "LOANPAYMENT"
//   },
//   {
//     "txnDetailsId": 2002,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "TAXILOAN",
//     "txnDetailsTypeName": "LOANDISBURSEMENT"
//   },
//   {
//     "txnDetailsId": 2003,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "TAXILOAN",
//     "txnDetailsTypeName": "LOANPAYMENT"
//   },
//   {
//     "txnDetailsId": 2004,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "MICROLOAN",
//     "txnDetailsTypeName": "LOANDISBURSEMENT"
//   },
//   {
//     "txnDetailsId": 2005,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "MICROLOAN",
//     "txnDetailsTypeName": "LOANPAYMENT"
//   },
//   {
//     "txnDetailsId": 2006,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "STAFFLOAN",
//     "txnDetailsTypeName": "LOANDISBURSEMENT"
//   },
//   {
//     "txnDetailsId": 2007,
//     "txnDetailsCat": "LOAN",
//     "txnDetailsFamilyName": "STAFFLOAN",
//     "txnDetailsTypeName": "LOANPAYMENT"
//   },
//   {
//     "txnDetailsId": 2008,
//     "txnDetailsCat": "SAVING",
//     "txnDetailsFamilyName": "INDIVIDUALSAVING",
//     "txnDetailsTypeName": "SAVINGDEPOSIT"
//   },
//   {
//     "txnDetailsId": 2009,
//     "txnDetailsCat": "SAVING",
//     "txnDetailsFamilyName": "INDIVIDUALSAVING",
//     "txnDetailsTypeName": "SAVINGWITHDRAWAL"
//   },
//   {
//     "txnDetailsId": 2010,
//     "txnDetailsCat": "FLOAT",
//     "txnDetailsFamilyName": "FLOANTXN",
//     "txnDetailsTypeName": "FLOATDEPOSIT"
//   },
//   {
//     "txnDetailsId": 2011,
//     "txnDetailsCat": "FLOAT",
//     "txnDetailsFamilyName": "FLOANTXN",
//     "txnDetailsTypeName": "FLOATWITHDRAWAL"
//   },
//   {
//     "txnDetailsId": 2012,
//     "txnDetailsCat": "EXPENSE",
//     "txnDetailsFamilyName": "EXPENSETXN",
//     "txnDetailsTypeName": "EXPENSETXN1"
//   }
              
           
            }else if(!posted){
              
              res.status(413).json(posted);
            }

     }else throw createError.PreconditionRequired('The specified transaction already exits!!')
}catch(error){
    console.table(error);
    next(error);
}
});


 router.post('/postTxnNonCustomer', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postTheTxnNonCustomer(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
});
         
   

   
   
   router.post('/postSetStationLoanTenure', auth.verifyAccessToken, async function(req, res,next) {
    try{
      
    res.json(await dbCon.postSetTheStationLoanTenure(req.body));
    
    
}catch(error){
    console.table(error);
    next(error);
}
});
   
 
 router.post('/postSetStationLoanLimit', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationLoanLimit(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});
 
  

  router.post('/postSetStationLoanInterestRate', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationLoanInterestRate(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});
  
    router.post('/postSetStationCommissionRate', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationCommissionRate(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});
    
        

   router.post('/postSetStationNumberOfDaysForAccrualInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationNumberOfDaysForAccrualInterest(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});   
   
   


   router.post('/getCustomerLoanDetails', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.getTheCustomerLoanDetails(req.body));
}catch(error){
    console.table(error);
    next(error);
}
}); 
   
  
    router.post('/postSetStationAmortType', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationAmortType(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});  
  
    router.post('/postSetStationAmortCycle', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postSetTheStationAmortCycle(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});  
    
   

    
 router.post('/postTxnCustomerApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
             if(!await dbCon.testWtherTxnExist(req.body)){
    res.json(await dbCon.postTheTxnCustomerApproval(req.body));
     }else throw createError.PreconditionRequired('The specified transaction already exits!!')
}catch(error){
    console.table(error);
    next(error);
}
});


 router.post('/postTxnNonCustomerApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postTheTxnNonCustomerApproval(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
});
   
       router.get('/getTxnsForApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheTxnsForApproval());
   
}catch(error){
    console.table(error);
    next(error);
}
});  

       router.post('/postApproveTxns', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postApproveTheTxns(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
}); 
       
           router.post('/postRejectTxns', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postRejectTheTxns(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
});


  router.get('/getSecurityType', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheSecurityType());
   
}catch(error){
    console.table(error);
    next(error);
}
});  
  
  
  
  

router.post('/postCreateLoanSecurity', auth.verifyAccessToken, async function(req, res,next) {
    try{
          if(Array.isArray(req.body)){
          
         res.json(await dbCon.postCreateTheLoanSecurity(req.body));
    }else throw createError.NotAcceptable(' The format of the payload should be an array');    
}catch(error){
    console.table(error);
    next(error);
}
});


router.post('/postCreateGaurantors', auth.verifyAccessToken, async function(req, res,next) {
    try{
          if(Array.isArray(req.body)){
          
         res.json(await dbCon.postCreateTheGaurantors(req.body));
    }else throw createError.NotAcceptable(' The format of the payload should be an array');    
}catch(error){
    console.table(error);
    next(error);
}
});



router.post('/postCreateMicroloanApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
          if(Array.isArray(req.body)){
          
        if(!await dbCon.checkWhetherTheApprovalMicroloanExits(req.body)){
          
         res.json(await dbCon.postCreateTheMicroloanApproval(req.body));
         
    }else throw createError.NotAcceptable('The microloan transaction for approval already exists');  
         
    }else throw createError.NotAcceptable('The format of the payload should be an array');    
          
}catch(error){
    console.table(error);
    next(error);
}
});

  router.get('/getTxnsForApprovalMicroloan', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheTxnsForApprovalMicroloan());
   
}catch(error){
    console.table(error);
    next(error);
}
}); 




      router.post('/postApproveTxnMicron', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postApproveTheTxnMicron(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
}); 
   
        router.post('/postRejectTxnMicron', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postRejectTheTxnMicron(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
}); 
         
 
  router.get('/getApprovedTxnsMicroloan', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getApprovedTxnsMicroloan());
   
}catch(error){
    console.table(error);
    next(error);
}
});      
    
    
 
  router.get('/getRejectedTxnsMicroloan', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheRejectedTxnsMicroloan());
   
}catch(error){
    console.table(error);
    next(error);
}
});   
  
    router.post('/postConfirmApprovedTxnMicron', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.postConfirmTheApprovedTxnMicron(req.body));
   
}catch(error){
    console.table(error);
    next(error);
}
});   
  
  
  
 
  router.post('/waiveInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
          
         if(!await dbCon.checkWhetherTheApprovalDetailsExits(req.body,'theInterestToBeWiaved')){
             
            //    if(!await dbCon.checkWhetherTheLoanExists(req.body,'theInterestToBeWiaved')){     
            
    res.json(await dbCon.waiveTheInterest(req.body));
    
        // }else throw createError.NotAcceptable('Loan Existance:The loan for which you are trying to waive interest does not exist!')
    
     }else throw createError.NotAcceptable('Request Exists:The request for waving the loan interest has already been initiated!')
       
  
}catch(error){
    console.table(error);
    next(error);
}
});  
  
  
  
 
    router.get('/getWaivedInterestsForApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheWaivedInterestsForApproval());
   
}catch(error){
    console.table(error);
    next(error);
}
});  

  
      router.post('/postApproveWaivedInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postApproveTheWaivedInterest(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});    





 
  router.post('/waivePrincimpal', auth.verifyAccessToken, async function(req, res,next) {
    try{
        
         if(!await dbCon.checkWhetherTheApprovalDetailsExits(req.body,'thePrincipalToBeWiaved')){
            
      res.json(await dbCon.waiveThePrincimpal(req.body));
     }else throw createError.NotAcceptable('The request for waving the loan principal has already been initiated!')  
 
}catch(error){
    console.table(error);
    next(error);
}
});  
  
    router.get('/getWaivedPrincipalForApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheWaivedPrincipalForApproval());
   
}catch(error){
    console.table(error);
    next(error);
}
});  

    
  
      router.post('/postApproveWaivedPrincipal', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postApproveTheWaivedPrincipal(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});    



 
  router.post('/ReverseInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
        
         if(!await dbCon.checkWhetherTheApprovalDetailsExits(req.body,'theInterestToBeReversed')){
            
         res.json(await dbCon.ReverseTheInterest(req.body));
     }else throw createError.NotAcceptable('The request for reversing the interest has already been initiated!')  

}catch(error){
    console.table(error);
    next(error);
}
});  
  
      
    router.get('/getReversedInterestsForApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheReversedInterestsForApproval());
   
}catch(error){
    console.table(error);
    next(error);
}
});  

 
  
      router.post('/postApproveReverseInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postApproveTheReverseInterest(req.body));
}catch(error){
    console.table(error);
    next(error);
}
});    
      
      
      
      
 
  router.post('/reversePrincimpal', auth.verifyAccessToken, async function(req, res,next) {
    try{
 
       if(!await dbCon.checkWhetherTheApprovalDetailsExits(req.body,'thePrincipalToBeReversed')){
            
        res.json(await dbCon.reverseThePrincimpal(req.body));
     }else throw createError.PreconditionRequired('The request for reversing the principal has already been initiated!') 
}catch(error){
    console.table(error);
    next(error);
}
});  
  

    router.get('/getReversedPrincipalForApproval', auth.verifyAccessToken, async function(req, res,next) {
    try{
   
    res.json(await dbCon.getTheReversedPrincipalForApproval());
   
}catch(error){
    console.table(error);
    next(error);
}
});  
    
  
      router.post('/postApproveReversePrincipal', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postApproveTheReversePrincipal(req.body));
}catch(error){
    console.table(error);
    next(error);
}
}); 
      
      
      
      
      
               router.post('/postRejectWaivedPrincipal', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postRejectTheWaivedPrincipal(req.body));
}catch(error){
    console.table(error);
    next(error);
}
}); 
  
          router.post('/postRejectReverseInterest', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.postRejectTheReverseInterest(req.body));
}catch(error){
    console.table(error);
    next(error);
}
}); 
          
     router.get('/getPrincipalTxnsForReversal', auth.verifyAccessToken, async function(req, res,next) {
    try{
    res.json(await dbCon.getThePrincipalTxnsForReversal(req.query.customerPhone1));
}catch(error){
    console.table(error);
    next(error);
}
});   
       
module.exports=router