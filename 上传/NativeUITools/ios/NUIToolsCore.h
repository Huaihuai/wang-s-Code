//
//  Header.h
//  pinkture
//
//  Created by alex chen on 12-9-3.
//  Copyright (c) 2012年 cimu. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <StoreKit/StoreKit.h>
#import "MBProgressHUD.h"

@interface NUIToolsCore : NSObject <UIAlertViewDelegate, MBProgressHUDDelegate, UIActionSheetDelegate, SKStoreProductViewControllerDelegate>
{
    NSString* m_returnText1;
    int m_returnBtn1;
    
    int m_ActionSheetRelBtn;
    
    MBProgressHUD *progressHub;
    MBProgressHUD *progressHubComplete;
    
    BOOL m_alertDialogShowing;
}

- (void)ShowInputDialogWithTitle:(NSString*)tTitle withMsg:(NSString*)tMessage  withPlaceholder:(NSString*)tHolder;
- (NSString*)GetInputDialogResult;

- (void)ShowAlertDialogWithTitle:(NSString*)tTitle withMsg:(NSString*)tMessage  withButtons:(NSString*)tButtons;
- (int)GetAlertDialogBtnResult;

-(void)ShowIndicatorWithType:(int)ttype withInfo:(NSString*)tinfo;
-(void)ShowIndicatorCompleteWith:(BOOL)tAnimated delay:(double)tDelay into:(NSString*)tInfo;
-(void)HideIndicator;

-(void)ShowActionSheet:(NSString*)tTitle withCancelBtn:(NSString*)tCancelBtn withDestructiveBtn:(NSString*)tDesBtn withButtons:(NSString*)tButtons;
-(int)GetActionSheetBtnResult;

-(BOOL)ShowAppStoreInfo:(NSString*)tAppId;
@end