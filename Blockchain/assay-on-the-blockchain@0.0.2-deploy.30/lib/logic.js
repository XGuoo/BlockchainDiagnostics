/**
* updateMdata function
* @param {assay-on-the-blockchain.updateMdata} tx The transaction that update the manufacture infomation.
* @transaction
*
*/
/*function updateMdata(tx) {

tx.device.state = "Unused";
  
    // Get the asset registry for the pizza asset.
    return getAssetRegistry('assay-on-the-blockchain.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}

/**
* updateMdata function
* @param {assay-on-the-blockchain.updateMdata} tx The transaction that update the manufacture infomation.
* @transaction
*
*/

/*function ChangeStateToUsed(tx) {

tx.device.state = "used";
  
    // Get the asset registry for the  asset.
    return getAssetRegistry('assay-on-the-blockchain.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}
*/

/**
* ChangeOwner function
* @param {assay-on-the-blockchain.ChangeOwner} tx The transaction that update the manufacture infomation.
* @transaction
*
*/
function ChangeOwner(tx) {

  tx.device.owner = tx.newOwner;
  
    // Get the asset registry for the pizza asset.
    return getAssetRegistry('assay-on-the-blockchain.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}

/*
*@param {assay-on-the-blockchain.ChangeStateToUsed} tx
*@transaction
*/
function ChangeStateToUsed(tx) {

tx.device.state = "Used";
  

    return getAssetRegistry('assay-on-the-blockchain.Device')
        .then(function (assetRegistry) {

            return assetRegistry.update(tx.device);
        });
}