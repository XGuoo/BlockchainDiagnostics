/**
* updateMdata function
* @param {org.assay.updateMdata} tx The transaction that update the manufacture infomation.
* @transaction
*
*/
/*function updateMdata(tx) {

tx.device.state = "Unused";
  
    // Get the asset registry for the pizza asset.
    return getAssetRegistry('org.assay.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}

/**
* updateMdata function
* @param {org.assay.updateMdata} tx The transaction that update the manufacture infomation.
* @transaction
*
*/

/*function ChangeStateToUsed(tx) {

tx.device.state = "used";
  
    // Get the asset registry for the  asset.
    return getAssetRegistry('org.assay.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}
*/

/**
* ChangeOwner function
* @param {org.assay.ChangeOwner} tx The transaction that update the manufacture infomation.
* @transaction
*
*/
function ChangeOwner(tx) {

  tx.device.owner = tx.newOwner;
  
    // Get the asset registry for the pizza asset.
    return getAssetRegistry('org.assay.Device')
        .then(function (assetRegistry) {

            // Update the asset in the pizza asset registry.
            return assetRegistry.update(tx.device);
        });
}

/*
*@param {org.assay.ChangeStateToUsed} tx
*@transaction
*/
function ChangeStateToUsed(tx) {

tx.device.state = "Used";
  

    return getAssetRegistry('org.assay.Device')
        .then(function (assetRegistry) {

            return assetRegistry.update(tx.device);
        });
}